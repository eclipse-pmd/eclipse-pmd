package ch.acanda.eclipse.pmd.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import ch.acanda.eclipse.pmd.PMDPlugin;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageRegistry;

/**
 * Analyzes files for coding problems, bugs and inefficient code, i.e. runs PMD.
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
    public void analyze(final IFile file, final List<RuleSet> ruleSets, final ViolationProcessor violationProcessor) {
        final List<RuleViolation> violations = runPMD(file, ruleSets);
        annotateFile(file, violationProcessor, violations);
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private List<RuleViolation> runPMD(final IFile file, final List<RuleSet> ruleSets) {
        try {
            if (isValidFile(file, ruleSets)) {
                final Language language = LANGUAGES.get(file.getFileExtension().toLowerCase(Locale.ROOT));
                if (isValidLanguage(language)) {
                    final PMDConfiguration configuration = new PMDConfiguration();
                    configuration.setDefaultLanguageVersion(language.getDefaultVersion());
                    configuration.setIgnoreIncrementalAnalysis(true);
                    try (PmdAnalysis analysis = PmdAnalysis.create(configuration); InputStream in = file.getContents()) {
                        analysis.addRuleSets(ruleSets);
                        analysis.files().addSourceFile(IOUtils.toString(in, file.getCharset()), file.getProjectRelativePath().toOSString());
                        return analysis.performAnalysisAndCollectReport().getViolations();
                    }
                }
            }
        } catch (final RuntimeException | CoreException | IOException e) {
            PMDPlugin.getDefault().warn("Could not run PMD on file " + file.getRawLocation(), e);
        }
        return List.of();
    }

    private void annotateFile(final IFile file, final ViolationProcessor violationProcessor, final List<RuleViolation> violations) {
        try {
            violationProcessor.annotate(file, violations);
        } catch (CoreException | IOException e) {
            PMDPlugin.getDefault().error("Could not annotate the file " + file.getRawLocation(), e);
        }
    }

    private boolean isValidFile(final IFile file, final List<RuleSet> ruleSets) {
        // derived (i.e. generated or compiled) files are not analyzed
        return !file.isDerived(IResource.CHECK_ANCESTORS)
                // the file must exist
                && file.isAccessible()
                // the file must have an extension so we can determine the language
                && file.getFileExtension() != null
                // the file must not be excluded in the pmd configuration
                && ruleSets.stream().anyMatch(rs -> rs.applies(file.getRawLocation().toFile()));
    }

    private boolean isValidLanguage(final Language language) {
        return language != null
                && language.getDefaultVersion() != null
                && language.getDefaultVersion().getLanguageVersionHandler() != null;
    }

}
