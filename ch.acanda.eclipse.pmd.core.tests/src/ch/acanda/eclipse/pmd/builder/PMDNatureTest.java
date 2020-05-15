package ch.acanda.eclipse.pmd.builder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * Unit tests for {@link PMDNature}.
 */
public class PMDNatureTest {

    /**
     * Verifies that {@link PMDNature#addTo(IProject)} appends the PMD nature to the list of other nature ids if the
     * project does not yet have it.
     */
    @Test
    public void addToAddsPMDNatureToProject() throws CoreException {
        final IProject project = createProject(false, "org.example.a", "org.example.b");

        PMDNature.addTo(project);

        final IProjectDescription description = project.getDescription();
        verify(project, times(1)).setDescription(same(description), isNull());
        verify(description, times(1)).setNatureIds(eq(new String[] { "org.example.a", "org.example.b", PMDNature.ID }));
    }

    private IProject createProject(final boolean hasNature, final String... natureIds) throws CoreException {
        final IProject project = mock(IProject.class);
        final IProjectDescription description = mock(IProjectDescription.class);
        when(project.getDescription()).thenReturn(description);
        when(project.hasNature(PMDNature.ID)).thenReturn(hasNature);
        when(description.getNatureIds()).thenReturn(natureIds);
        return project;
    }

    /**
     * Verifies that {@link PMDNature#addTo(IProject)} does not change the nature ids if the project already has it.
     */
    @Test
    public void addToDoesNotAddPMDNatureToProject() throws CoreException {
        final IProject project = createProject(true, "org.example.a", PMDNature.ID, "org.example.b");

        PMDNature.addTo(project);

        final IProjectDescription description = project.getDescription();
        verify(project, never()).setDescription(any(IProjectDescription.class), isNull());
        verify(description, never()).setNatureIds(any(String[].class));
    }

    /**
     * Verifies that {@link PMDNature#removeFrom(IProject)} removes the PMD nature if the project already has it and
     * that it keeps the remaining nature ids in the same order.
     */
    @Test
    public void removeFromRemovesPMDNatureFromProject() throws CoreException {
        final IProject project = createProject(true, "org.example.a", PMDNature.ID, "org.example.b");

        PMDNature.removeFrom(project);

        final IProjectDescription description = project.getDescription();
        verify(project, times(1)).setDescription(same(description), isNull());
        verify(description, times(1)).setNatureIds(eq(new String[] { "org.example.a", "org.example.b" }));
    }

    /**
     * Verifies that {@link PMDNature#removeFrom(IProject)} does not change the nature ids if the project does not have
     * the PMD nature.
     */
    @Test
    public void removeFromDoesNotRemovePMDNatureFromProject() throws CoreException {
        final IProject project = createProject(false, "org.example.a", "org.example.b");

        PMDNature.removeFrom(project);

        final IProjectDescription description = project.getDescription();
        verify(project, never()).setDescription(any(IProjectDescription.class), isNull());
        verify(description, never()).setNatureIds(any(String[].class));
    }

}
