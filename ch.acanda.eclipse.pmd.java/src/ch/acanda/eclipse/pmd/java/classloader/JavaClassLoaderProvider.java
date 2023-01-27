package ch.acanda.eclipse.pmd.java.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import ch.acanda.eclipse.pmd.extension.PMDClassLoaderProvider;
import net.sourceforge.pmd.lang.Language;

public class JavaClassLoaderProvider implements PMDClassLoaderProvider {

    private static final ClassLoaderCache CACHE = new ClassLoaderCache();

    @Override
    public Optional<ClassLoader> getClassLoader(final IFile file, final Language language) {
        if ("java".equals(language.getTerseName())) {
            return Optional.ofNullable(CACHE.get(file));
        }
        return Optional.empty();
    }


    private static class ClassLoaderCache {

        private final Map<String, CachedClassLoader> cache = new HashMap<>();

        @SuppressWarnings("PMD.UseProperClassLoader")
        ClassLoader get(final IFile file) {
            final String key = file.getProject().getFullPath().toString();
            final CachedClassLoader value = cache.get(key);
            if (value == null || !value.isExpired()) {
                final ClassLoader cl = getClassLoader(file);
                cache.put(key, new CachedClassLoader(cl));
                return cl;
            }
            return value.getClassLoader();
        }

        private ClassLoader getClassLoader(final IFile file) {
            final IJavaProject project = JavaCore.create(file.getProject());
            if (project != null) {
                try {
                    final List<URL> urls = new ArrayList<>();
                    urls.add(getDefaultOutputLocation(project, file));
                    urls.addAll(getClasspathEntryOutputLocations(project));
                    return new URLClassLoader(urls.toArray(URL[]::new));
                } catch (final MalformedURLException | JavaModelException e) {
                    // TODO: log exception
                }
            }
            return null;
        }

        private List<URL> getClasspathEntryOutputLocations(final IJavaProject project)
                throws JavaModelException, MalformedURLException {
            return Stream.of(project.getResolvedClasspath(true))
                    .map(entry -> entry.getOutputLocation())
                    .filter(Objects::nonNull)
                    .map(loc -> toURL(loc))
                    .filter(Objects::nonNull)
                    .toList();
        }

        private URL toURL(final IPath path) {
            try {
                return path.toFile().getAbsoluteFile().toURI().toURL();
            } catch (final MalformedURLException e) {
                return null;
            }
        }

        private URL getDefaultOutputLocation(final IJavaProject project, final IFile file)
                throws MalformedURLException, JavaModelException {
            return file.getWorkspace()
                    .getRoot()
                    .getFolder(project.getOutputLocation())
                    .getRawLocation().toFile().toURI().toURL();
        }

    }

    private static class CachedClassLoader {
        private final ClassLoader classLoader;
        private final long createdAt = System.currentTimeMillis();

        CachedClassLoader(final ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        boolean isExpired() {
            return System.currentTimeMillis() - createdAt <= 300_000;
        }

        ClassLoader getClassLoader() {
            return classLoader;
        }
    }

}
