package ch.acanda.eclipse.pmd.extension;

import java.util.Optional;

import org.eclipse.core.resources.IFile;

import net.sourceforge.pmd.lang.Language;

public interface PMDClassLoaderProvider {

    /**
     * Optionally returns a class loader for processing the PMD rules for provided file and language.
     *
     * @param file The file that is about to be processed. Never null.
     * @param language The language of the file that is about to be processed. Never null.
     */
    Optional<ClassLoader> getClassLoader(IFile file, Language language);

}
