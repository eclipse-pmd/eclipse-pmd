package ch.acanda.eclipse.pmd.java.classpath;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageRegistry;

public class JavaAuxClasspathProviderTest {

    private static final String JRE_17 = "/jre17/jrt-fs.jar";
    private static final String JRE_21 = "/jre21/jrt-fs.jar";

    private final Language java = LanguageRegistry.PMD.getLanguageById("java");

    @BeforeEach
    public void setUp() throws CoreException {
        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
        if (project.exists()) {
            project.close(null);
            project.delete(true, true, null);
        }
        project.create(null);
        project.open(null);
    }

    @AfterEach
    public void tearDown() throws CoreException {
        for (final IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
            project.close(null);
            project.delete(true, true, null);
        }
    }

    @Test
    public void shouldIncludeOutputFolder() throws CoreException {
        final IFile file = createTestFile();
        final List<String> actual = new JavaAuxClasspathProvider().getAuxClasspath(file, java).toList();
        final List<String> expected = List.of(absoluteOutputPath(file.getProject()));
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void shouldIncludeProjectJRE() throws CoreException {
        final IFile file = createTestFile(library(JRE_21));
        final List<String> actual = new JavaAuxClasspathProvider().getAuxClasspath(file, java).toList();
        final List<String> expected = List.of(
                absoluteOutputPath(file.getProject()),
                new File(JRE_21).getAbsolutePath()
        );
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void shouldIncludeLibrary() throws CoreException {
        final IFile file = createTestFile(library("/lib/foo.jar"));
        final List<String> actual = new JavaAuxClasspathProvider().getAuxClasspath(file, java).toList();
        final List<String> expected = List.of(
                absoluteOutputPath(file.getProject()),
                new File("/lib/foo.jar").getAbsolutePath()
        );
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void shouldIncludeDependencyOutputFolder() throws CoreException {
        final IFile file = createTestFile(project("dependency"));
        final List<String> actual = new JavaAuxClasspathProvider().getAuxClasspath(file, java).toList();
        final List<String> expected = Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects())
                .map(JavaAuxClasspathProviderTest::absoluteOutputPath).toList();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void shouldNotIncludeDependencyJRE() throws CoreException {
        final IClasspathEntry dependencyProject = project("dependency", library(JRE_17));
        final IFile file = createTestFile(dependencyProject, library(JRE_21));
        final List<String> actual = new JavaAuxClasspathProvider().getAuxClasspath(file, java).toList();
        final Stream<String> outputPaths = Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects())
                .map(JavaAuxClasspathProviderTest::absoluteOutputPath);
        final List<String> expected = Stream.concat(outputPaths, Stream.of(new File(JRE_21).getAbsolutePath())).toList();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void shouldIncludeDependencyLibrary() throws CoreException {
        final IClasspathEntry dependencyProject = project("dependency", library("/lib/bar.jar"));
        final IFile file = createTestFile(dependencyProject);
        final List<String> actual = new JavaAuxClasspathProvider().getAuxClasspath(file, java).toList();
        final Stream<String> outputPaths = Stream.of(ResourcesPlugin.getWorkspace().getRoot().getProjects())
                .map(JavaAuxClasspathProviderTest::absoluteOutputPath);
        final List<String> expected = Stream.concat(outputPaths, Stream.of(new File("/lib/bar.jar").getAbsolutePath())).toList();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void shouldInvalidateCacheWhenClasspathChanges() throws CoreException {
        final IFile file = createTestFile(library(JRE_21));
        final JavaAuxClasspathProvider provider = new JavaAuxClasspathProvider();
        List<String> actual = provider.getAuxClasspath(file, java).toList();
        assertThat(actual).contains(new File(JRE_21).getAbsolutePath());
        JavaCore.create(file.getProject()).setRawClasspath(new IClasspathEntry[] { library(JRE_17) }, null);
        actual = provider.getAuxClasspath(file, java).toList();
        assertThat(actual).contains(new File(JRE_17).getAbsolutePath());
    }

    private static IFile createTestFile(final IClasspathEntry... classpath) throws CoreException {
        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
        final IProjectDescription description = project.getDescription();
        description.setNatureIds(new String[] { JavaCore.NATURE_ID });
        project.setDescription(description, null);
        final IJavaProject javaProject = JavaCore.create(project);
        javaProject.setRawClasspath(classpath, null);
        return project.getFile("Test.java");
    }

    private static IClasspathEntry library(final String path) {
        return JavaCore.newLibraryEntry(new Path(path), null, null);
    }

    private static IClasspathEntry project(final String name, final IClasspathEntry... classpath) throws CoreException {
        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
        project.create(null);
        project.open(null);
        final IProjectDescription description = project.getDescription();
        description.setNatureIds(new String[] { JavaCore.NATURE_ID });
        project.setDescription(description, null);
        JavaCore.create(project).setRawClasspath(classpath, null);
        return JavaCore.newProjectEntry(project.getFullPath());
    }

    private static String absoluteOutputPath(final IProject project) {
        try {
            final IPath folder = JavaCore.create(project).getOutputLocation();
            return ResourcesPlugin.getWorkspace().getRoot().getFolder(folder).getRawLocation().makeAbsolute().toOSString();
        } catch (final JavaModelException e) {
            throw new AssertionError("Unable to get output location for project " + project.getName(), e);
        }
    }

}
