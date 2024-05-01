package ch.acanda.eclipse.pmd.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.acanda.eclipse.pmd.domain.Location;
import ch.acanda.eclipse.pmd.domain.LocationContext;
import ch.acanda.eclipse.pmd.domain.RuleSetModel;
import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

public class PMDPropertyPageModelTransformerTest {

    private static final String RULESET_XML = """
            <?xml version="1.0" encoding="UTF-8" standalone="no"?>
            <ruleset xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" name="eclipse-pmd rules"
                xsi:noNamespaceSchemaLocation="http://pmd.sourceforge.net/ruleset_2_0_0.xsd"
                xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0 http://pmd.sourceforge.net/ruleset_2_0_0.xsd"
                xmlns="http://pmd.sourceforge.net/ruleset/2.0.0">
            </ruleset>
            """;

    /**
     * Verifies that {@link PMDPropertyPageModelTransformer#toViewModel(RuleSetModel, IProject)} can convert a properly
     * set up model with a workspace location.
     */
    @Test
    public void createViewModelForWorspaceModel(@TempDir final Path projectPath) throws IOException {
        final Path ruleSet = Files.createFile(projectPath.resolve("rs.xml"));
        Files.writeString(ruleSet, RULESET_XML);
        final IProject project = mock(IProject.class);
        final IWorkspace workspace = mock(IWorkspace.class);
        final IWorkspaceRoot root = mock(IWorkspaceRoot.class);
        when(project.getName()).thenReturn("Foo");
        when(project.getLocationURI()).thenReturn(projectPath.toUri());
        when(project.getWorkspace()).thenReturn(workspace);
        when(workspace.getRoot()).thenReturn(root);
        when(root.getProject("Foo")).thenReturn(project);
        final RuleSetModel model = new RuleSetModel("A", new Location("Foo/rs.xml", LocationContext.WORKSPACE));

        final RuleSetViewModel viewModel = PMDPropertyPageModelTransformer.toViewModel(model, project);

        assertEquals("A", viewModel.getName(), "name");
        assertEquals("Workspace", viewModel.getType(), "type");
        assertEquals("Foo/rs.xml", viewModel.getLocation(), "location");
        assertEquals(true, viewModel.isLocationValid(), "isLlocationValid");
        assertEquals(ruleSet.toAbsolutePath().toString(), viewModel.getResolvedPath(), "resolvedPath");
        assertEquals(null, viewModel.getRuleSetErrorMessage(), "ruleSetErrorMessage");
    }

    /**
     * Verifies that {@link PMDPropertyPageModelTransformer#toViewModel(RuleSetModel, IProject)} can convert a model
     * with an invalid workspace location.
     */
    @Test
    public void createViewModelForModelWithInvalidWorkspaceLocation() throws IOException {
        final IProject project = mock(IProject.class);
        final IWorkspace workspace = mock(IWorkspace.class);
        final IWorkspaceRoot root = mock(IWorkspaceRoot.class);
        when(project.getName()).thenReturn("InvalidFoo");
        when(project.getLocationURI()).thenReturn(null);
        when(project.getWorkspace()).thenReturn(workspace);
        when(workspace.getRoot()).thenReturn(root);
        when(root.getProject("InvalidFoo")).thenReturn(project);
        final RuleSetModel model = new RuleSetModel("A", new Location("InvalidFoo/rs.xml", LocationContext.WORKSPACE));

        final RuleSetViewModel viewModel = PMDPropertyPageModelTransformer.toViewModel(model, project);

        assertEquals("A", viewModel.getName(), "name");
        assertEquals("Workspace", viewModel.getType(), "type");
        assertEquals("InvalidFoo/rs.xml", viewModel.getLocation(), "location");
        assertEquals(false, viewModel.isLocationValid(), "isLlocationValid");
        assertEquals(null, viewModel.getResolvedPath(), "resolvedPath");
        assertEquals(null, viewModel.getRuleSetErrorMessage(), "ruleSetErrorMessage");
    }

}
