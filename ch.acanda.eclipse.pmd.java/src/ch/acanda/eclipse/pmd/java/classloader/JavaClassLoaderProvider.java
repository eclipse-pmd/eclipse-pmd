package ch.acanda.eclipse.pmd.java.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import ch.acanda.eclipse.pmd.extension.PMDClassLoaderProvider;
import net.sourceforge.pmd.lang.Language;

public class JavaClassLoaderProvider implements PMDClassLoaderProvider {

    @Override
    @SuppressWarnings("PMD.UseProperClassLoader")
    public Optional<ClassLoader> getClassLoader(final IFile file, final Language language) {
        if ("java".equals(language.getTerseName())) {
            final IJavaProject project = JavaCore.create(file.getProject());
            if (project != null) {
                try {
                    final List<URL> urls = new ArrayList<>();
                    urls.add(getDefaultOutputLocation(project, file));
                    addClasspathEntryOutputLocations(urls, project);
                    return Optional.of(new URLClassLoader(urls.toArray(URL[]::new), language.getClass().getClassLoader()));
                } catch (final MalformedURLException | JavaModelException e) {
                    // TODO: log exception
                }
            }
        }
        return Optional.empty();
    }

    private void addClasspathEntryOutputLocations(final List<URL> urls, final IJavaProject project)
            throws JavaModelException, MalformedURLException {
        for (final IClasspathEntry entry : project.getResolvedClasspath(true)) {
            final IPath output = entry.getOutputLocation();
            if (output != null) {
                urls.add(output.toFile().getAbsoluteFile().toURI().toURL());
            }
        }
    }

    private URL getDefaultOutputLocation(final IJavaProject project, final IFile file) throws MalformedURLException, JavaModelException {
        return file.getWorkspace()
                .getRoot()
                .getFolder(project.getOutputLocation())
                .getRawLocation().toFile().toURI().toURL();
    }

}
