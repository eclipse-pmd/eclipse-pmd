package ch.acanda.eclipse.pmd.java.resolution;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Position;
import org.eclipse.text.edits.TextEdit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.marker.WrappingPMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Base class for testing quick fix tests based on {@link ASTQuickFix}. An extending class must provide a static method
 * with the annotation {@link org.junit.jupiter.params.Parameters} that returns the parameters for the test case, e.g:
 *
 * <pre>
 * &#064;Parameters
 * public static Collection&lt;Object[]&gt; getTestData() {
 *     return createTestData(ExtendsObjectQuickFixTest.class.getResourceAsStream(&quot;ExtendsObject.xml&quot;));
 * }
 * </pre>
 *
 * The easiest way to implement this method is to use {@link QuickFixTestData#createTestData(InputStream)} and provide
 * an {@code InputStream} to an XML file containing all the test data. See {@link QuickFixTestData} for the format of
 * the XML file. See {@link ch.acanda.eclipse.pmd.java.resolution.codestyle.ExtendsObjectQuickFixTest
 * ExtendsObjectQuickFixTest} for a complete example.
 *
 * @param <T> The type of the quick fix.
 */
@SuppressWarnings({ "PMD.AbstractClassWithoutAbstractMethod", "PMD.ExcessiveImports" })
public abstract class ASTQuickFixTestCase<T extends ASTQuickFix<? extends ASTNode>> {

    @SuppressWarnings("unchecked")
    private ASTQuickFix<ASTNode> getQuickFix(final TestParameters params) {
        try {
            final Type typeArgument = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            final Class<T> quickFixClass = (Class<T>) typeArgument;
            final IMarker marker = mock(IMarker.class);
            when(marker.getAttribute(eq("ruleName"), isA(String.class))).thenReturn(params.rulename.orElse(null));
            final String markerText = params.source.substring(params.offset, params.offset + params.length);
            if (!markerText.contains("\n")) {
                when(marker.getAttribute(eq("markerText"), isA(String.class))).thenReturn(markerText);
            }
            return (ASTQuickFix<ASTNode>) quickFixClass.getConstructor(PMDMarker.class).newInstance(new WrappingPMDMarker(marker));
        } catch (SecurityException | ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static List<Object[]> createTestData(final InputStream testCase) {
        return QuickFixTestData.createTestData(testCase).stream().map(params -> new Object[] { params }).collect(toList());
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void apply(final TestParameters params) throws BadLocationException {
        final ASTQuickFix<ASTNode> quickFix = getQuickFix(params);
        final org.eclipse.jface.text.Document document = new org.eclipse.jface.text.Document(params.source);
        final CompilationUnit ast = createAST(document, quickFix, params);
        final ASTNode node = findNode(params, ast, quickFix);

        quickFix.apply(node);

        final String actual = rewriteAST(document, ast);
        assertEquals(params.expectedSource, actual,
                () -> "Result of applying the quick fix " + quickFix.getClass().getSimpleName() + " to the test " + params.name);
    }

    private ASTNode findNode(final TestParameters params, final CompilationUnit ast, final ASTQuickFix<ASTNode> quickFix) {
        final Class<? extends ASTNode> nodeType = quickFix.getNodeType();
        final NodeFinder<CompilationUnit, ASTNode> finder = quickFix.getNodeFinder(new Position(params.offset, params.length));
        final Optional<ASTNode> node = finder.findNode(ast);
        assertTrue(node.isPresent(),
                () -> "Couldn't find node of type " + nodeType.getSimpleName() + "."
                        + " Check the position of the marker in test " + params.name + ".");
        return node.get();
    }

    private CompilationUnit createAST(final org.eclipse.jface.text.Document document, final ASTQuickFix<ASTNode> quickFix,
            final TestParameters params) {
        final ASTParser astParser = ASTParser.newParser(AST.getJLSLatest());
        astParser.setSource(document.get().toCharArray());
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        astParser.setResolveBindings(quickFix.needsTypeResolution());
        astParser.setEnvironment(new String[0], new String[0], new String[0], true);
        final String name = last(params.pmdReferenceId.orElse("QuickFixTest").split("/"));
        astParser.setUnitName(format("{0}.java", name));
        final String version = last(params.language.orElse("java 17").split("\\s+"));
        astParser.setCompilerOptions(Map.of(
                JavaCore.COMPILER_SOURCE, version,
                JavaCore.COMPILER_COMPLIANCE, version,
                JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, version));
        final CompilationUnit ast = (CompilationUnit) astParser.createAST(null);
        ast.recordModifications();
        return ast;
    }

    private String last(final String... strings) {
        return strings[strings.length - 1];
    }

    private String rewriteAST(final org.eclipse.jface.text.Document document, final CompilationUnit ast) throws BadLocationException {
        final TextEdit edit = ast.rewrite(document, getRewriteOptions());
        edit.apply(document);
        return document.get();
    }

    private Map<String, String> getRewriteOptions() {
        final Map<String, String> options = new HashMap<>();
        options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
        options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_SIZE, "4");
        options.put(DefaultCodeFormatterConstants.FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_SWITCH, DefaultCodeFormatterConstants.TRUE);
        options.put(DefaultCodeFormatterConstants.FORMATTER_INDENT_SWITCHSTATEMENTS_COMPARE_TO_CASES, DefaultCodeFormatterConstants.TRUE);
        options.put(DefaultCodeFormatterConstants.FORMATTER_INDENT_BREAKS_COMPARE_TO_CASES, DefaultCodeFormatterConstants.TRUE);
        options.put(DefaultCodeFormatterConstants.FORMATTER_INSERT_SPACE_BEFORE_ASSIGNMENT_OPERATOR, JavaCore.DO_NOT_INSERT);
        options.put(DefaultCodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_ASSIGNMENT_OPERATOR, JavaCore.DO_NOT_INSERT);
        options.put(DefaultCodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ANNOTATION, JavaCore.INSERT);
        options.put(DefaultCodeFormatterConstants.FORMATTER_INSERT_SPACE_AFTER_COMMA_IN_ARRAY_INITIALIZER, JavaCore.DO_NOT_INSERT);
        options.put(DefaultCodeFormatterConstants.FORMATTER_INSERT_NEW_LINE_AFTER_ANNOTATION_ON_PARAMETER, JavaCore.DO_NOT_INSERT);
        return options;
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldReturnExpectedIcon(final TestParameters params) throws IllegalAccessException, NoSuchFieldException {
        final ImageDescriptor imageDescriptor = getQuickFix(params).getImageDescriptor();
        if (params.expectedImage.isPresent()) {
            final Field field = PMDPluginImages.class.getDeclaredField(params.expectedImage.get());
            assertEquals(field.get(null), imageDescriptor, () -> "Quick fix image descriptor in test " + params.name);
        } else {
            assertNotNull(imageDescriptor, () -> "Quick fix image descriptor must not be null (test " + params.name + ")");
        }
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldReturnExpectedLabel(final TestParameters params) {
        final String label = getQuickFix(params).getLabel();
        if (params.expectedLabel.isPresent()) {
            assertEquals(params.expectedLabel.get(), label, () -> "Quick fix label in test " + params.name);
        } else {
            assertNotNull(label, () -> "Quick fix label must not be null (test " + params.name + ")");
        }
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldReturnExpectedDescription(final TestParameters params) {
        final String description = getQuickFix(params).getDescription();
        if (params.expectedDescription.isPresent()) {
            assertEquals(params.expectedDescription.get(), description, () -> "Quick fix description in test " + params.name);
        } else {
            assertNotNull(description, () -> "Quick fix description must not be null (test " + params.name + ")");
        }
    }

}
