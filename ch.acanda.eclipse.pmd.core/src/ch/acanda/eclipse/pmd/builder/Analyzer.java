package ch.acanda.eclipse.pmd.builder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.SAXParseException;

import ch.acanda.eclipse.pmd.PMDPlugin;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PMDException;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.SourceCodeProcessor;
import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.ast.ParseException;

/**
 * Analyzes files for coding problems, bugs and inefficient code, i.e. runs PMD.
 *
 * @author Philip Graf
 */
public final class Analyzer {

    private static final Map<String, Language> LANGUAGES = LanguageRegistry.getLanguages().stream()
            .flatMap(language -> language.getExtensions().stream().map(extension -> Map.entry(extension, language)))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    /**
     * Analyzes a single file.
     *
     * @param file The file to analyze.
     * @param ruleSets The rule sets against the file will be analyzed.
     * @param violationProcessor The processor that processes the violated rules.
     */
    public void analyze(final IFile file, final RuleSets ruleSets, final ViolationProcessor violationProcessor) {
        final Iterable<RuleViolation> violations = runPMD(file, ruleSets);
        annotateFile(file, violationProcessor, violations);
    }

    private Iterable<RuleViolation> runPMD(final IFile file, final RuleSets ruleSets) {
        try {
            if (isValidFile(file, ruleSets)) {
                final Language language = LANGUAGES.get(file.getFileExtension().toLowerCase(Locale.ROOT));
                if (isValidLanguage(language)) {
                    final PMDConfiguration configuration = new PMDConfiguration();
                    try (InputStreamReader reader = new InputStreamReader(file.getContents(), file.getCharset())) {
                        final RuleContext context = PMD.newRuleContext(file.getName(), file.getRawLocation().toFile());
                        context.setLanguageVersion(language.getDefaultVersion());
                        context.setIgnoreExceptions(false);
                        new SourceCodeProcessor(configuration).processSourceCode(reader, ruleSets, context);
                        return () -> context.getReport().iterator();
                    }
                }
            }
        } catch (CoreException | IOException e) {
            PMDPlugin.getDefault().error("Could not run PMD on file " + file.getRawLocation(), e);
        } catch (final PMDException e) {
            if (isIncorrectSyntaxCause(e)) {
                PMDPlugin.getDefault().info("Could not run PMD because of incorrect syntax of file " + file.getRawLocation(), e);
            } else {
                PMDPlugin.getDefault().warn("Could not run PMD on file " + file.getRawLocation(), e);
            }
        }
        return List.of();
    }

    private void annotateFile(final IFile file, final ViolationProcessor violationProcessor, final Iterable<RuleViolation> violations) {
        try {
            violationProcessor.annotate(file, violations);
        } catch (CoreException | IOException e) {
            PMDPlugin.getDefault().error("Could not annotate the file " + file.getRawLocation(), e);
        }
    }

    private boolean isValidFile(final IFile file, final RuleSets ruleSets) {
        // derived (i.e. generated or compiled) files are not analyzed
        return !file.isDerived(IResource.CHECK_ANCESTORS)
                // the file must exist
                && file.isAccessible()
                // the file must have an extension so we can determine the language
                && file.getFileExtension() != null
                // the file must not be excluded in the pmd configuration
                && ruleSets.applies(file.getRawLocation().toFile());
    }

    private boolean isValidLanguage(final Language language) {
        return language != null
                && language.getDefaultVersion() != null
                && language.getDefaultVersion().getLanguageVersionHandler() != null;
    }

    private boolean isIncorrectSyntaxCause(final PMDException e) {
        final Throwable cause = e.getCause();
        // syntax of a Java or JSP file is incorrect
        return cause instanceof ParseException
                // syntax of an XML file is incorrect
                || cause instanceof SAXParseException;
    }

}
