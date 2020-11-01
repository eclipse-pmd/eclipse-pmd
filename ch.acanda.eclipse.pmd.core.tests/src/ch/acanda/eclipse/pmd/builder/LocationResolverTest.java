package ch.acanda.eclipse.pmd.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.junit.jupiter.api.Test;

import ch.acanda.eclipse.pmd.domain.Location;
import ch.acanda.eclipse.pmd.domain.LocationContext;

/**
 * Unit tests for {@code LocationResolver}.
 */
public class LocationResolverTest {

    private static final String XML = ".xml";

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} resolves the loacation in a file
     * system context correctly.
     */
    @Test
    public void resolveIfExistsFileSystemLocation() throws IOException {
        final Path ruleSetFile = Files.createTempFile(LocationResolverTest.class.getSimpleName(), XML);
        try {
            final Location location = new Location(ruleSetFile.toString(), LocationContext.FILE_SYSTEM);
            final IProject project = mock(IProject.class);

            final Optional<String> result = LocationResolver.resolveIfExists(location, project);

            assertTrue(result.isPresent(), "A valid file system location should resolve");
            assertEquals(ruleSetFile.toString(), result.get(),
                    "The resolved location in a file system context should be the provided location");
        } finally {
            Files.deleteIfExists(ruleSetFile);
        }
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not resolve the loacation in a
     * file system context when the file is mossing.
     */
    @Test
    public void resolveIfExistsFileSystemLocationWithMissingFile() {
        final Location location = new Location("/tmp/pmd.xml", LocationContext.FILE_SYSTEM);
        final IProject project = mock(IProject.class);

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a file
     * system context if the path is invalid.
     */
    @Test
    public void resolveIfExistsFileSystemLocationWithInvalidPath() {
        final Location location = new Location("\u0000:", LocationContext.FILE_SYSTEM);
        final IProject project = mock(IProject.class);

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} resolves the location in a remote
     * context correctly.
     */
    @Test
    public void resolveIfExistsRemoteLocation() throws IOException {
        final Path ruleSetFile = Files.createTempFile(LocationResolverTest.class.getSimpleName(), XML);
        try {
            final Location location = new Location(ruleSetFile.toUri().toString(), LocationContext.REMOTE);
            final IProject project = mock(IProject.class);

            final Optional<String> result = LocationResolver.resolveIfExists(location, project);

            assertTrue(result.isPresent(), "A valid remote location should resolve");
            assertEquals(ruleSetFile.toUri().toString(), result.get(),
                    "The resolved location in a remote context should be the provided location");
        } finally {
            Files.deleteIfExists(ruleSetFile);
        }
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not resolve the location in a
     * remote context when the file does not exist.
     */
    @Test
    public void resolveIfExistsRemoteLocationWithMissingFile() {
        final Location location = new Location("http://example.org/pmd.xml", LocationContext.REMOTE);
        final IProject project = mock(IProject.class);

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a
     * remote context if the URI is invalid.
     */
    @Test
    public void resolveIfExistsRemoteLocationWithInvalidURI() {
        final Location location = new Location("http:#", LocationContext.REMOTE);
        final IProject project = mock(IProject.class);

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} resolves the location in a project
     * context correctly.
     */
    @Test
    public void resolveIfExistsProjectLocation() throws URISyntaxException, IOException {
        final Path ruleSetFile = Files.createTempFile(LocationResolverTest.class.getSimpleName(), XML);
        try {
            final Location location = new Location(ruleSetFile.getFileName().toString(), LocationContext.PROJECT);
            final IProject project = mock(IProject.class);
            when(project.getLocationURI()).thenReturn(ruleSetFile.getParent().toUri());

            final Optional<String> result = LocationResolver.resolveIfExists(location, project);

            assertTrue(result.isPresent(), "A valid project location should resolve");
            assertEquals(ruleSetFile.toString(), result.get(),
                    "The resolved location in a project context should be the provided location appended to the project location");
        } finally {
            Files.deleteIfExists(ruleSetFile);
        }
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not resolve the location in a
     * project context when the rule set file does not exist.
     */
    @Test
    public void resolveIfExistsProjectLocationWithMissingFile() throws URISyntaxException {
        final Location location = new Location("pmd.xml", LocationContext.PROJECT);
        final IProject project = mock(IProject.class);
        when(project.getLocationURI()).thenReturn(new URI("file:///workspace/project/"));

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a
     * project context if the path is invalid.
     */
    @Test
    public void resolveIfExistsProjectLocationWithInvalidPath() throws URISyntaxException {
        final Location location = new Location("\u0000:", LocationContext.PROJECT);
        final IProject project = mock(IProject.class);
        when(project.getLocationURI()).thenReturn(new URI("file:///workspace/project/"));

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} resolves the location in a workspace
     * context correctly.
     */
    @Test
    public void resolveIfExistsWorkspaceLocation() throws URISyntaxException, IOException {
        final Path ruleSetFile = Files.createTempFile(LocationResolverTest.class.getSimpleName(), XML);
        try {
            final Location location = new Location("project/" + ruleSetFile.getFileName().toString(), LocationContext.WORKSPACE);
            final IProject project = mock(IProject.class);
            final IWorkspace workspace = mock(IWorkspace.class);
            final IWorkspaceRoot workspaceRoot = mock(IWorkspaceRoot.class);
            when(project.getWorkspace()).thenReturn(workspace);
            when(workspace.getRoot()).thenReturn(workspaceRoot);
            when(workspaceRoot.getProject("project")).thenReturn(project);
            when(project.getLocationURI()).thenReturn(ruleSetFile.getParent().toUri());

            final Optional<String> result = LocationResolver.resolveIfExists(location, project);

            assertTrue(result.isPresent(), "A valid workspace location should resolve");
            assertEquals(ruleSetFile.toString(), result.get(),
                    "The resolved location in a workspace context should be the provided location appended to the workspace location");
        } finally {
            Files.deleteIfExists(ruleSetFile);
        }
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a
     * workspace context if the rule set file does not exist.
     */
    @Test
    public void resolveIfExistsWorkspaceLocationWithMissingFile() throws URISyntaxException {
        final Location location = new Location("project/pmd.xml", LocationContext.WORKSPACE);
        final IProject project = createProject("project", new URI("file:///workspace/project"));

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a
     * workspace context if the project does not exist.
     */
    @Test
    public void resolveIfExistsWorkspaceLocationWithMissingProject() {
        final Location location = new Location("MissingProject/pmd.xml", LocationContext.WORKSPACE);
        final IProject project = createProject("MissingProject", null);

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a
     * workspace context if the path contains invalid characters.
     */
    @Test
    public void resolveIfExistsWorkspaceLocationWithInvalidPath() throws URISyntaxException {
        final Location location = new Location("project/\u0000:", LocationContext.WORKSPACE);
        final IProject project = createProject("project", new URI("file:///workspace/project/"));

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a
     * workspace context if the path consists only of the project name.
     */
    @Test
    public void resolveIfExistsWorkspaceLocationWithProjectNameOnly() throws URISyntaxException {
        final Location location = new Location("project", LocationContext.WORKSPACE);
        final IProject project = createProject("project", new URI("file:///workspace/project/"));

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolveIfExists(Location, IProject)} does not throw an exception in a
     * workspace context if the path is empty.
     */
    @Test
    public void resolveIfExistsWorkspaceLocationWithoutPath() throws URISyntaxException {
        final Location location = new Location("", LocationContext.WORKSPACE);
        final IProject project = createProject("project", new URI("file:///workspace/project/"));

        final Optional<String> result = LocationResolver.resolveIfExists(location, project);

        assertFalse(result.isPresent(), "The location should not resolve");
    }

    /**
     * Verifies that {@link LocationResolver#resolve(Location, IProject)} resolves the location in a project context
     * correctly.
     */
    @Test
    public void resolveWorkspaceLocation() throws URISyntaxException {
        final Location location = new Location("project/path/pmd.xml", LocationContext.WORKSPACE);
        final IProject project = createProject("project", new URI("file:///workspace/project/"));

        final String result = LocationResolver.resolve(location, project);

        assertEquals(Paths.get("/workspace", "project", "path", "pmd.xml"), Paths.get(result),
                "The resolved location should be the project's path with the location's path appended");
    }

    private static IProject createProject(final String name, final URI uri) {
        final IProject project = mock(IProject.class);
        final IWorkspace workspace = mock(IWorkspace.class);
        final IWorkspaceRoot workspaceRoot = mock(IWorkspaceRoot.class);
        when(project.getWorkspace()).thenReturn(workspace);
        when(workspace.getRoot()).thenReturn(workspaceRoot);
        when(workspaceRoot.getProject(name)).thenReturn(project);
        when(project.getLocationURI()).thenReturn(uri);
        return project;
    }

    /**
     * Verifies that {@link LocationResolver#resolve(Location, IProject)} resolves the location in a project context
     * correctly.
     */
    @Test
    public void resolveProjectLocation() throws URISyntaxException {
        final Location location = new Location("path/pmd.xml", LocationContext.PROJECT);
        final IProject project = mock(IProject.class);
        when(project.getLocationURI()).thenReturn(new URI("file:///workspace/project/"));

        final String result = LocationResolver.resolve(location, project);

        assertEquals(Paths.get("/workspace", "project", "path", "pmd.xml"), Paths.get(result),
                "The resolved location should be the project's path with the location's path appended");
    }

    /**
     * Verifies that {@link LocationResolver#resolve(Location, IProject)} resolves the location in a file system context
     * correctly.
     */
    @Test
    public void resolveFileSystemLocation() {
        final Location location = new Location("/some/path/pmd.xml", LocationContext.FILE_SYSTEM);
        final IProject project = mock(IProject.class);

        final String result = LocationResolver.resolve(location, project);

        assertEquals("/some/path/pmd.xml", result, "The resolved location should just be the path");
    }

    /**
     * Verifies that {@link LocationResolver#resolve(Location, IProject)} resolves the location in a remote context
     * correctly.
     */
    @Test
    public void resolveRemoteLocation() {
        final Location location = new Location("http://example.org/pmd.xml", LocationContext.FILE_SYSTEM);
        final IProject project = mock(IProject.class);

        final String result = LocationResolver.resolve(location, project);

        assertEquals("http://example.org/pmd.xml", result, "The resolved location should be the URL");
    }
}
