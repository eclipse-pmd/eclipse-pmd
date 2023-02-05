package ch.acanda.eclipse.pmd.java.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import ch.acanda.eclipse.pmd.extension.PMDClassLoaderProvider;
import ch.acanda.eclipse.pmd.java.PMDJavaPlugin;
import net.sourceforge.pmd.PmdAnalysis;
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

        @SuppressWarnings("PMD.UseProperClassLoader")
        private ClassLoader getClassLoader(final IFile file) {
            final IProject project = file.getProject();
            final Collection<IPath> projects = new ArrayList<>();
            projects.add(project.getFullPath());
            final URL[] classpath = getClasspath(project, projects).toArray(URL[]::new);
            return classpath.length == 0 ? null : new URLClassLoader(classpath, PmdAnalysis.class.getClassLoader());
        }

        private Stream<URL> getClasspath(final IProject project, final Collection<IPath> projects) {
            final IJavaProject javaProject = JavaCore.create(project);
            if (javaProject != null) {
                try {
                    final Stream<URL> defaultOutput = Stream.of(getDefaultOutputLocation(javaProject));
                    final Stream<URL> entries = getClasspathEntries(javaProject, projects);
                    return Stream.concat(defaultOutput, entries);
                } catch (final JavaModelException | MalformedURLException e) {
                    PMDJavaPlugin.getDefault().warn("Failed to create classpath for project " + project.getName(), e);
                }
            }
            return Stream.empty();
        }

        private Stream<URL> getClasspathEntries(final IJavaProject project, final Collection<IPath> projects)
                throws JavaModelException, MalformedURLException {
            return Stream.of(project.getResolvedClasspath(true)).flatMap(entry -> getClassesLocation(entry, project, projects));
        }

        private Stream<URL> getClassesLocation(final IClasspathEntry entry, final IJavaProject project, final Collection<IPath> projects) {
            final int kind = entry.getEntryKind();
            if (kind == IClasspathEntry.CPE_SOURCE) {
                return toURL(entry.getOutputLocation());
            }
            if (kind == IClasspathEntry.CPE_LIBRARY) {
                return toURL(entry.getPath());
            }
            if (kind == IClasspathEntry.CPE_PROJECT) {
                final IProject cpProject =
                        project.getProject().getWorkspace().getRoot().getProject(entry.getPath().toString());
                if (!projects.contains(cpProject.getFullPath())) {
                    projects.add(cpProject.getFullPath());
                    return getClasspath(cpProject, projects);
                }
            }
            return null;
        }

        private Stream<URL> toURL(final IPath path) {
            if (path != null) {
                try {
                    return Stream.of(path.toFile().getAbsoluteFile().toURI().toURL());
                } catch (final MalformedURLException e) {
                    PMDJavaPlugin.getDefault().warn("Failed to create URL for path " + path, e);
                }
            }
            return Stream.empty();
        }

        private URL getDefaultOutputLocation(final IJavaProject project) throws MalformedURLException, JavaModelException {
            return project.getProject()
                    .getWorkspace().getRoot()
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
