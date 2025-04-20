package ch.acanda.eclipse.pmd.extension;

import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;

import net.sourceforge.pmd.lang.Language;

@FunctionalInterface
public interface PMDAuxClasspathProvider {

    /**
     * Returns the auxiliary classpath for processing the PMD rules for the provided file and language.
     *
     * @param file The file that is about to be processed. Never null.
     * @param language The language of the file that is about to be processed. Never null.
     * @see https://pmd.github.io/pmd/pmd_languages_java.html#providing-the-auxiliary-classpath
     */
    Stream<String> getAuxClasspath(IFile file, Language language);

}
