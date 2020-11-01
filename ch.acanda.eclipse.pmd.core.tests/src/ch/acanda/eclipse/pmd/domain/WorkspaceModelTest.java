package ch.acanda.eclipse.pmd.domain;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ch.acanda.eclipse.pmd.domain.DomainModel.AddElementPropertyChangeEvent;
import ch.acanda.eclipse.pmd.domain.DomainModel.RemoveElementPropertyChangeEvent;

/**
 * Unit tests for {@link WorkspaceModel}.
 */
public class WorkspaceModelTest {

    /**
     * Verifies that {@link WorkspaceModel#getProjects()} returns an empty set when there aren't any projects in the
     * workspace.
     */
    @Test
    public void whenThereAreNoProjectsSetProjectsReturnsAnEmptySet() {
        assertTrue(new WorkspaceModel().getProjects().isEmpty(),
                "When there areen't any projects, getProjects() should return an empty set");
    }

    /**
     * Verifies that an event is fired when adding a project model.
     */
    @Test
    public void addFiresAnAddElementPropertyChangeEvent() {
        final WorkspaceModel model = new WorkspaceModel();
        final boolean[] eventFired = new boolean[1];
        final ProjectModel element = new ProjectModel("Foo");
        model.addPropertyChangeListener(WorkspaceModel.PROJECTS_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                eventFired[0] = true;
                assertTrue(event instanceof AddElementPropertyChangeEvent, "The event should be an AddElementPropertyChangeEvent");
                assertSame(element, ((AddElementPropertyChangeEvent) event).getAddedElement(), "Event's added element");
                assertNull(event.getOldValue(), "Event's old value should be null");
                assertSame(element, event.getNewValue(), "Event's new value should be the added element");
            }
        });

        model.add(element);

        assertTrue(eventFired[0], "An event should be fired when adding a project model");
    }

    /**
     * Verifies that an event is fired when removing a project model.
     */
    @Test
    public void removeFiresARemoveElementPropertyChangeEvent() {
        final WorkspaceModel model = new WorkspaceModel();
        final boolean[] eventFired = new boolean[1];
        final ProjectModel element = new ProjectModel("Foo");
        model.add(element);
        model.addPropertyChangeListener(WorkspaceModel.PROJECTS_PROPERTY, new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                eventFired[0] = true;
                assertTrue(event instanceof RemoveElementPropertyChangeEvent, "The event should be an RemoveElementPropertyChangeEvent");
                assertSame(element, ((RemoveElementPropertyChangeEvent) event).getRemovedElement(), "Event's added element");
                assertSame(element, event.getOldValue(), "Event's old value should be the removed element");
                assertNull(event.getNewValue(), "Event's new value should be null");
            }
        });

        model.remove(element.getProjectName());

        assertTrue(eventFired[0], "An event should be fired when removing a project model");
    }

    /**
     * Verifies that no event is fired when removing an inexistent project model.
     */
    @Test
    public void removeDoesNotFireARemoveElementPropertyChangeEventForInexistentProject() {
        final WorkspaceModel model = new WorkspaceModel();
        final boolean[] eventFired = new boolean[1];
        model.addPropertyChangeListener(WorkspaceModel.PROJECTS_PROPERTY, event -> eventFired[0] = true);

        model.remove("Bar");

        assertFalse(eventFired[0], "An event should not be fired when removing an inexistent project model");
    }

    /**
     * Verifies that {@link WorkspaceModel#getProject(String)} returns {@code Optional.absent()} when the requested
     * project model does not exist.
     */
    @Test
    public void returnsAbsentWhenProjectModelDoesNotExist() {
        final WorkspaceModel model = new WorkspaceModel();

        final Optional<ProjectModel> actual = model.getProject("Foo");

        assertNotNull(actual, "WorkspaceModel.getProject(...) must never return null");
        assertFalse(actual.isPresent(), "The project model should not be present");
    }

    /**
     * Verifies that {@link WorkspaceModel#getProject(String)} returns the requested project model.
     */
    @Test
    public void returnsRequestedProjectModel() {
        final WorkspaceModel model = new WorkspaceModel();
        final ProjectModel expected = new ProjectModel("Foo");
        model.add(expected);

        final Optional<ProjectModel> actual = model.getProject(expected.getProjectName());

        assertNotNull(actual, "WorkspaceModel.getProject(...) must never return null");
        assertTrue(actual.isPresent(), "The project model should be present");
        assertSame(expected, actual.get(), "WorkspaceModel.getProject(...) should return the requested project model");
    }

    /**
     * Verifies that {@link WorkspaceModel#getOrCreateProject(String)} returns a new project model when the requested
     * project model does not yet exist.
     */
    @Test
    public void returnsOptionalAbsentWhenProjectModelDoesNotExist() {
        final WorkspaceModel model = new WorkspaceModel();

        final ProjectModel actual = model.getOrCreateProject("Foo");

        assertNotNull(actual, "WorkspaceModel.getOrCreateProject(...) must never return null");
        assertEquals("Foo", actual.getProjectName(), "Project model name");
        assertSame(model.getProject("Foo").get(), actual, "WorkspaceModel.getOrCreateProject(...) should add the created project model");
    }

    /**
     * Verifies that {@link WorkspaceModel#getOrCreateProject(String)} returns the requested project model when it
     * already exists.
     */
    @Test
    public void returnsRequestedProjectModelWhenProjectModelExists() {
        final WorkspaceModel model = new WorkspaceModel();
        final ProjectModel expected = new ProjectModel("Foo");
        model.add(expected);

        final ProjectModel actual = model.getOrCreateProject(expected.getProjectName());

        assertNotNull(actual, "WorkspaceModel.getOrCreateProject(...) must never return null");
        assertSame(actual, expected, "WorkspaceModel.getOrCreateProject(...) should return the requested project model");
    }

}
