package ch.acanda.eclipse.pmd.builder;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSetNotFoundException;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.RulesetsFactoryUtils;

/**
 * Unit tests for {@link Analyzer}.
 */
@SuppressWarnings("PMD.ExcessiveImports")
public class AnalyzerTest {

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze Java files.
     */
    @Test
    public void analyzeJava() {
        analyze("class A extends Object {}", UTF_8, "java", "rulesets/java/basic.xml/ExtendsObject", "ExtendsObject");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all Java rules.
     */
    @Test
    public void analyzeJavaAllRules() throws IOException {
        final String content = "/** */\n"
                + "package a;\n"
                + "/** */\n"
                + "public class FooBar {\n"
                + "  /** */\n"
                + "  public FooBar() {\n"
                + "    baz();\n"
                + "  }\n"
                + "  private void baz() {\n"
                + "    /* */\n"
                + "  }\n"
                + "}\n";
        analyze(content, UTF_8, "java", getAllRuleSetRefIds("java"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze xml files.
     */
    @Test
    public void analyzeXML() {
        analyze("<cdata><![CDATA[[bar]]></cdata>", UTF_8, "xml", "rulesets/xml/basic.xml/MistypedCDATASection", "MistypedCDATASection");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all xml rules.
     */
    @Test
    public void analyzeXMLAllRules() throws IOException {
        analyze("<a/>", UTF_8, "xml", getAllRuleSetRefIds("xml"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze jsp files.
     */
    @Test
    public void analyzeJSP() {
        analyze("<jsp:forward page='a.jsp'/>", UTF_8, "jsp", "rulesets/jsp/basic.xml/NoJspForward", "NoJspForward");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all jsp rules.
     */
    @Test
    public void analyzeJSPAllRules() throws IOException {
        analyze("<%@ page contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\" %>", UTF_8, "jsp", getAllRuleSetRefIds("jsp"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze modelica files.
     */
    @Test
    public void analyzeModelica() {
        analyze("model A\nend B;", UTF_8, "mo",
                "category/modelica/bestpractices.xml/ClassStartNameEqualsEndName",
                "ClassStartNameEqualsEndName");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all modelica rules.
     */
    @Test
    public void analyzeModelicaAllRules() throws IOException {
        analyze("model A\nend A;", UTF_8, "mo", getAllRuleSetRefIds("modelica"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze xsl files.
     */
    @Test
    public void analyzeXSL() {
        analyze("<variable name=\"var\" select=\"//item/descendant::child\"/>", UTF_8, "xsl",
                "rulesets/xsl/xpath.xml/AvoidAxisNavigation", "AvoidAxisNavigation");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all jsp rules.
     */
    @Test
    public void analyzeXSLAllRules() throws IOException {
        analyze("<a/>", UTF_8, "xsl", getAllRuleSetRefIds("xsl"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze Ecmascript files.
     */
    @Test
    public void analyzeEcmascript() {
        analyze("var z = 1.12345678901234567", UTF_8, "js",
                "rulesets/ecmascript/basic.xml/InnaccurateNumericLiteral", "InnaccurateNumericLiteral");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all ecmascript rules.
     */
    @Test
    public void analyzeEcmascriptAllRules() throws IOException {
        analyze("var i = 0", UTF_8, "js", getAllRuleSetRefIds("ecmascript"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze Velocity files.
     */
    @Test
    public void analyzeVelocity() {
        analyze("<script type=\"text/javascript\">$s</script>", UTF_8, "vm", "rulesets/vm/basic.xml/NoInlineJavaScript",
                "NoInlineJavaScript");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all Velocity rules.
     */
    @Test
    public void analyzeVelocityAllRules() throws IOException {
        analyze("<a/>", UTF_8, "vm", getAllRuleSetRefIds("vm"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze PLSQL files.
     */
    @Test
    public void analyzePLSQL() {
        final String content = "select * from a";
        analyze(content, UTF_8, "sql", "category/plsql/codestyle.xml/CodeFormat", "CodeFormat");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all PLSQL rules.
     */
    @Test
    public void analyzePLSQLAllRules() throws IOException {
        analyze("select *\n  from a", UTF_8, "sql", getAllRuleSetRefIds("plsql"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can analyze Apex files.
     */
    @Test
    public void analyzeApex() {
        final String content = "public class apex { }";
        analyze(content, UTF_8, "cls", "category/apex/codestyle.xml/ClassNamingConventions", "ClassNamingConventions");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} can run all Apex rules.
     */
    @Test
    public void analyzeApexAllRules() throws IOException {
        analyze("/** @description Hello Apex */ public class Apex { }", UTF_8, "cls", getAllRuleSetRefIds("apex"));
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} doesn't throw a NullPointerException
     * when the file to analyze does not have a file extension.
     */
    @Test
    public void analyzeFileWithoutExtension() {
        analyze("Hello World", UTF_8, null, "rulesets/java/basic.xml/ExtendsObject");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} doesn't throw a NullPointerException
     * when trying to analyze a class file.
     */
    @Test
    public void analyzeClassFile() {
        analyze("", UTF_8, "class", "rulesets/java/basic.xml/ExtendsObject");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} doesn't analyze a derived file.
     */
    @Test
    public void analyzeDerivedFile() throws UnsupportedEncodingException, CoreException {
        final IFile file = mockFile("class A extends Object {}", UTF_8, "java", true, true);
        analyze(file, "rulesets/java/basic.xml/ExtendsObject");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} doesn't analyze an inaccessible file.
     */
    @Test
    public void analyzeInaccessibleFile() throws UnsupportedEncodingException, CoreException {
        final IFile file = mockFile("", UTF_8, "java", false, false);
        analyze(file, "rulesets/java/basic.xml/ExtendsObject");
    }

    /**
     * Verifies that {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor)} works around PMD's
     * <a href="http://sourceforge.net/p/pmd/bugs/1076/">bug #1076</a> and reports two violations instead of only one.
     */
    @Test
    public void analyzePMDBug1076() throws UnsupportedEncodingException, CoreException {
        final IFile file = mockFile("class Foo { void bar(int a, int b) { } }", UTF_8, "java", false, true);
        analyze(file, "rulesets/java/optimizations.xml/MethodArgumentCouldBeFinal",
                "MethodArgumentCouldBeFinal", "MethodArgumentCouldBeFinal");
    }

    /**
     * Prepares the arguments, calls {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor), and verifies that it
     * invokes {@link ViolationProcessor#annotate(IFile, Iterable) with the correct rule violations.
     */
    private void analyze(final String content, final Charset charset, final String fileExtension, final String ruleSetRefId,
            final String... violatedRules) {
        try {
            final IFile file = mockFile(content, charset, fileExtension, false, true);
            analyze(file, ruleSetRefId, violatedRules);
        } catch (CoreException | IOException e) {
            throw new AssertionError("Failed to mock file", e);
        }
    }

    /**
     * Prepares the arguments, calls {@link Analyzer#analyze(IFile, RuleSets, ViolationProcessor), and verifies that it
     * invokes {@link ViolationProcessor#annotate(IFile, Iterable) with the correct rule violations and that it invokes
     * {@link RuleSets#start(RuleContext)} as well as {@link RuleSets#end(RuleContext)} if the file is valid.
     */
    public void analyze(final IFile file, final String ruleSetRefId, final String... violatedRules) {
        try {
            final ViolationProcessor violationProcessor = mock(ViolationProcessor.class);
            final RuleSets ruleSets = spy(RulesetsFactoryUtils.defaultFactory().createRuleSets(ruleSetRefId));
            new Analyzer().analyze(file, ruleSets, violationProcessor);

            verify(violationProcessor).annotate(same(file), violations(violatedRules));

            final boolean isValidFile = violatedRules.length > 0;
            if (isValidFile) {
                verify(ruleSets).start(any(RuleContext.class));
                verify(ruleSets).end(any(RuleContext.class));
            }

        } catch (final RuleSetNotFoundException e) {
            throw new AssertionError("Failed to create rule sets", e);

        } catch (CoreException | IOException e) {
            throw new AssertionError("Failed to annotate file", e);
        }
    }

    private IFile mockFile(final String content, final Charset charset, final String fileExtension, final boolean isDerived,
            final boolean isAccessible) throws CoreException, UnsupportedEncodingException {
        final IFile file = mock(IFile.class);
        when(file.isDerived(IResource.CHECK_ANCESTORS)).thenReturn(isDerived);
        when(file.isAccessible()).thenReturn(isAccessible);
        when(file.getFileExtension()).thenReturn(fileExtension);
        when(file.getCharset()).thenReturn(charset.name());
        when(file.getContents()).thenReturn(new ByteArrayInputStream(content.getBytes(charset)));
        final IPath path = mock(IPath.class);
        when(file.getRawLocation()).thenReturn(path);
        when(path.toFile()).thenReturn(new File("test." + fileExtension));
        return file;
    }

    private String getAllRuleSetRefIds(final String language) throws IOException {
        final String categories = "/category/" + language + "/categories.properties";
        try (InputStream in = PMD.class.getResourceAsStream(categories)) {
            assertNotNull("Resource " + categories + " not found", in);
            final Properties properties = new Properties();
            properties.load(in);
            return properties.getProperty("rulesets.filenames");
        }
    }

    private List<RuleViolation> violations(final String... ruleNames) {
        return argThat(new RuleViolationIteratorMatcher(ruleNames));
    }

    private static class RuleViolationIteratorMatcher implements ArgumentMatcher<List<RuleViolation>> {

        private final Iterable<String> expectedRuleNames;

        public RuleViolationIteratorMatcher(final String... ruleNames) {
            expectedRuleNames = List.of(ruleNames);
        }

        @Override
        public boolean matches(final List<RuleViolation> violations) {
            return violations.stream()
                    .flatMap(violation -> Optional.ofNullable(violation.getRule()).map(Rule::getName).stream())
                    .collect(Collectors.toList())
                    .equals(expectedRuleNames);
        }

    }
}
