package ch.acanda.eclipse.pmd.java.classpath;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import ch.acanda.eclipse.pmd.extension.PMDAuxClasspathProvider;
import ch.acanda.eclipse.pmd.java.PMDJavaPlugin;
import net.sourceforge.pmd.lang.Language;

public class JavaAuxClasspathProvider implements PMDAuxClasspathProvider {

    private static final String JAVA_LANGUAGE_ID = "java";
    static final String CLASSPATH_FILE = ".classpath";
    private final ClasspathCache cache = new ClasspathCache();

    public JavaAuxClasspathProvider() {
        JavaCore.addPreProcessingResourceChangedListener(new ClasspathChangeListener(), IResourceChangeEvent.POST_CHANGE);
    }

    private final class ClasspathChangeListener implements IResourceChangeListener {
        @Override
        public void resourceChanged(final IResourceChangeEvent event) {
            try {
                event.getDelta().accept(delta -> {
                    if (delta.getResource() instanceof IFile) {
                        final IFile file = (IFile) delta.getResource();
                        if (CLASSPATH_FILE.equals(file.getName())) {
                            cache.remove(file.getProject());
                            return false;
                        }
                    }
                    return true;

                });
            } catch (final CoreException e) {
                PMDJavaPlugin.getLogger().warn("Failed to process resource change event for ClasspathChangeListener", e);
            }
        }

    }

    @Override
    public Stream<String> getAuxClasspath(final IFile file, final Language language) {
        if (JAVA_LANGUAGE_ID.equals(language.getId())) {
            final Stream<String> classpath = cache.get(file);
            return classpath != null ? classpath : Stream.empty();
        }
        return Stream.empty();
    }

    private static final class ClasspathCache {

        private final Map<String, CachedClasspath> cache = new HashMap<>();

        Stream<String> get(final IFile file) {
            final String key = file.getProject().getFullPath().toString();
            final CachedClasspath value = cache.get(key);
            if (value == null || !value.isExpired()) {
                final List<String> cp = getClasspath(file).toList();
                cache.put(key, new CachedClasspath(cp));
                return cp.stream();
            }
            return value.getClasspath().stream();
        }

        void remove(final IProject project) {
            final String key = project.getFullPath().toString();
            cache.remove(key);
        }

        private Stream<String> getClasspath(final IFile file) {
            final IProject project = file.getProject();
            final Collection<IPath> projects = new ArrayList<>();
            projects.add(project.getFullPath());
            return getClasspath(project, projects, true);
        }

        private Stream<String> getClasspath(final IProject project, final Collection<IPath> projects, final boolean isRootProject) {
            final IJavaProject javaProject = JavaCore.create(project);
            if (javaProject != null) {
                try {
                    final Stream<String> defaultOutput = Stream.of(getDefaultOutputLocation(javaProject));
                    final Stream<String> entries = getClasspathEntries(javaProject, projects, isRootProject);
                    return Stream.concat(defaultOutput, entries);
                } catch (final JavaModelException | MalformedURLException e) {
                    PMDJavaPlugin.getLogger().warn("Failed to create classpath for project " + project.getName(), e);
                }
            }
            return Stream.empty();
        }

        private Stream<String> getClasspathEntries(
                final IJavaProject project, final Collection<IPath> projects,
                final boolean isRootProject
        ) throws JavaModelException {
            return Stream.of(project.getResolvedClasspath(true))
                    .flatMap(entry -> getClassesLocation(entry, project, projects, isRootProject));
        }

        private Stream<String> getClassesLocation(
                final IClasspathEntry entry, final IJavaProject project,
                final Collection<IPath> projects, final boolean isRootProject
        ) {
            final int kind = entry.getEntryKind();
            if (kind == IClasspathEntry.CPE_SOURCE) {
                return toClasspathEntry(entry.getOutputLocation());
            }
            if (kind == IClasspathEntry.CPE_LIBRARY && !isDependencyJRE(entry, isRootProject)) {
                return toClasspathEntry(entry.getPath());
            }
            if (kind == IClasspathEntry.CPE_PROJECT) {
                final IProject cpProject =
                        project.getProject().getWorkspace().getRoot().getProject(entry.getPath().toString());
                if (!projects.contains(cpProject.getFullPath())) {
                    projects.add(cpProject.getFullPath());
                    return getClasspath(cpProject, projects, false);
                }
            }
            return Stream.empty();
        }

        private boolean isDependencyJRE(final IClasspathEntry entry, final boolean isRootProject) {
            final String last = entry.getPath().lastSegment();
            return !isRootProject && ("jrt-fs.jar".equals(last) || "rt.jar".equals(last));
        }

        private Stream<String> toClasspathEntry(final IPath path) {
            if (path != null) {
                return Stream.of(path.toFile().getAbsolutePath());
            }
            return Stream.empty();
        }

        private String getDefaultOutputLocation(final IJavaProject project) throws MalformedURLException, JavaModelException {
            return project.getProject()
                    .getWorkspace().getRoot()
                    .getFolder(project.getOutputLocation())
                    .getRawLocation().toFile().getAbsolutePath();
        }

    }

    private static class CachedClasspath {
        private final List<String> classpath;
        private final long createdAt = System.currentTimeMillis();

        CachedClasspath(final List<String> classpath) {
            this.classpath = classpath;
        }

        boolean isExpired() {
            return System.currentTimeMillis() - createdAt <= 300_000;
        }

        List<String> getClasspath() {
            return classpath;
        }
    }

}
