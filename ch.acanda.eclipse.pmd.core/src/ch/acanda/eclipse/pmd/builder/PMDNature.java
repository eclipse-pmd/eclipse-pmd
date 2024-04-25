package ch.acanda.eclipse.pmd.builder;

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import ch.acanda.eclipse.pmd.marker.MarkerUtil;

/**
 * Project nature of projects that have PMD enabled.
 */
public class PMDNature implements IProjectNature {

    public static final String ID = "ch.acanda.eclipse.pmd.builder.PMDNature";

    private IProject project;

    @Override
    public void configure() throws CoreException {
        final IProjectDescription desc = project.getDescription();
        final ICommand[] commands = desc.getBuildSpec();

        for (final ICommand command : commands) {
            if (Objects.equals(command.getBuilderName(), PMDBuilder.ID)) {
                return;
            }
        }

        final ICommand[] newCommands = new ICommand[commands.length + 1];
        System.arraycopy(commands, 0, newCommands, 0, commands.length);
        final ICommand command = desc.newCommand();
        command.setBuilderName(PMDBuilder.ID);
        newCommands[newCommands.length - 1] = command;
        desc.setBuildSpec(newCommands);
        project.setDescription(desc, null);
    }

    @Override
    public void deconfigure() throws CoreException {
        final IProjectDescription description = getProject().getDescription();
        final ICommand[] commands = description.getBuildSpec();
        for (int i = 0; i < commands.length; ++i) {
            if (Objects.equals(commands[i].getBuilderName(), PMDBuilder.ID)) {
                final ICommand[] newCommands = new ICommand[commands.length - 1];
                System.arraycopy(commands, 0, newCommands, 0, i);
                System.arraycopy(commands, i + 1, newCommands, i,
                        commands.length - i - 1);
                description.setBuildSpec(newCommands);
                project.setDescription(description, null);
                return;
            }
        }
    }

    @Override
    public IProject getProject() {
        return project;
    }

    @Override
    public void setProject(final IProject project) {
        this.project = project;
    }

    /**
     * Adds the PMD nature to a project.
     */
    public static void addTo(final IProject project) throws CoreException {
        if (!project.hasNature(ID)) {
            final IProjectDescription description = project.getDescription();
            description.setNatureIds(Stream.concat(Stream.of(description.getNatureIds()), Stream.of(ID)).toArray(String[]::new));
            project.setDescription(description, null);
            MarkerUtil.removeAllMarkers(project);
        }
    }

    /**
     * Removes the PMD nature from a project.
     */
    public static void removeFrom(final IProject project) throws CoreException {
        if (project.hasNature(ID)) {
            final IProjectDescription description = project.getDescription();
            description.setNatureIds(Stream.of(description.getNatureIds()).filter(id -> !ID.equals(id)).toArray(String[]::new));
            project.setDescription(description, null);
            MarkerUtil.removeAllMarkers(project);
        }
    }

}
