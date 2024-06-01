package ch.acanda.eclipse.pmd.cache;

import static ch.acanda.eclipse.pmd.domain.ProjectModel.RULESETS_PROPERTY;
import static ch.acanda.eclipse.pmd.domain.WorkspaceModel.PROJECTS_PROPERTY;
import static java.util.concurrent.TimeUnit.HOURS;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.builder.LocationResolver;
import ch.acanda.eclipse.pmd.domain.DomainModel.AddElementPropertyChangeEvent;
import ch.acanda.eclipse.pmd.domain.DomainModel.RemoveElementPropertyChangeEvent;
import ch.acanda.eclipse.pmd.domain.LocationContext;
import ch.acanda.eclipse.pmd.domain.ProjectModel;
import ch.acanda.eclipse.pmd.domain.WorkspaceModel;
import ch.acanda.eclipse.pmd.file.FileChangedListener;
import ch.acanda.eclipse.pmd.file.FileWatcher;
import ch.acanda.eclipse.pmd.file.Subscription;
import net.sourceforge.pmd.lang.rule.RuleSet;

/**
 * The rule set cache caches the PMD rule sets so they do not have to be rebuilt every time PMD is invoked.
 */
public final class RuleSetsCache {

    /**
     * Maps a project name to the project's rule sets.
     */
    private final LoadingCache<String, List<RuleSet>> cache;

    private final ProjectModelListener projectModelListener = new ProjectModelListener();

    private final Optional<FileWatcher> fileWatcher;

    private final Map<String, List<Subscription>> subscriptions = new HashMap<>();

    public RuleSetsCache(final CacheLoader<String, List<RuleSet>> loader, final WorkspaceModel workspaceModel) {
        // by expiring the rule sets we make sure to notice changes in remote configurations
        cache = CacheBuilder.newBuilder().expireAfterWrite(1, HOURS).build(loader);

        fileWatcher = createFileWatcher();

        for (final ProjectModel projectModel : workspaceModel.getProjects()) {
            projectModel.addPropertyChangeListener(/* RULESETS_PROPERTY, */projectModelListener);
            startWatchingRuleSetFiles(projectModel);
        }
        workspaceModel.addPropertyChangeListener(PROJECTS_PROPERTY, new WorkspaceModelListener());
    }

    private void startWatchingRuleSetFiles(final ProjectModel projectModel) {
        if (fileWatcher.isPresent() && projectModel.isPMDEnabled()) {
            final FileChangedListener listener = new RuleSetFileListener(projectModel);
            final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectModel.getProjectName());
            projectModel.getRuleSets().stream()
                    .filter(rs -> rs.getLocation().getContext() != LocationContext.REMOTE)
                    .flatMap(rs -> LocationResolver.resolveIfExists(rs.getLocation(), project).stream())
                    .forEach(location -> startWatchingRuleSetFile(projectModel.getProjectName(), Paths.get(location), listener));
        }
    }

    private void startWatchingRuleSetFile(final String projectName, final Path file, final FileChangedListener listener) {
        try {
            final Subscription subscription = fileWatcher.get().subscribe(file, listener);
            subscriptions.computeIfAbsent(projectName, name -> new ArrayList<>()).add(subscription);
        } catch (final IOException e) {
            final String msg = "Cannot watch rule set file %s. Changes to this file will not be picked up for up to an hour.";
            PMDPlugin.getLogger().warn(String.format(msg, file.toAbsolutePath()), e);
        }
    }

    private void stopWatchingRuleSetFiles(final ProjectModel projectModel) {
        final List<Subscription> list = subscriptions.remove(projectModel.getProjectName());
        if (list != null) {
            list.forEach(Subscription::cancel);
        }
    }

    private Optional<FileWatcher> createFileWatcher() {
        // false positive in PMD 7.2.0
        @SuppressWarnings("PMD.LocalVariableCouldBeFinal")
        Optional<FileWatcher> optionalFileWatcher;
        try {
            optionalFileWatcher = Optional.of(new FileWatcher());
        } catch (final IOException e) {
            optionalFileWatcher = Optional.empty();
        }
        return optionalFileWatcher;
    }

    /**
     * Returns the PMD rule sets of the provided project. The rule sets are taken from the cache if already available or
     * loaded from the repository if not.
     *
     * @param projectName The name of the project.
     * @return The PMD rule sets of the project.
     */
    public List<RuleSet> getRuleSets(final String projectName) {
        return cache.getUnchecked(projectName);
    }

    /**
     * Invalidates the cache entry for the project with the provided name, i.e. the next time
     * {@link #getRuleSets(String)} is called, the rule sets are loaded from their source.
     *
     * @param projectName The name of the project.
     */
    private void invalidate(final String projectName) {
        PMDPlugin.getLogger().info("Invalidating cache for " + projectName);
        cache.invalidate(projectName);
    }

    /**
     * Keeps track of added and removed project models.
     */
    private final class WorkspaceModelListener implements PropertyChangeListener {
        @Override
        public void propertyChange(final PropertyChangeEvent event) {

            if (event instanceof AddElementPropertyChangeEvent) {
                // A project has been added. Add a listener to invalidate its cache entry when its rule sets change
                final ProjectModel projectModel = (ProjectModel) ((AddElementPropertyChangeEvent) event).getAddedElement();
                projectModel.addPropertyChangeListener(/* RULESETS_PROPERTY, */projectModelListener);
                startWatchingRuleSetFiles(projectModel);

            } else if (event instanceof RemoveElementPropertyChangeEvent) {
                // A project has been removed. Invalidate it's cache entry to release the cached resources.
                final ProjectModel projectModel = (ProjectModel) ((RemoveElementPropertyChangeEvent) event).getRemovedElement();
                invalidate(projectModel.getProjectName());
                projectModel.removePropertyChangeListener(RULESETS_PROPERTY, projectModelListener);
                stopWatchingRuleSetFiles(projectModel);
            }
        }
    }

    /**
     * Invalidates the cache entry of a project if there have been made any changes to the respective project model so
     * its rule set is rebuilt based on the new model data the next time it is used.
     */
    private final class ProjectModelListener implements PropertyChangeListener {

        @Override
        public void propertyChange(final PropertyChangeEvent event) {
            final ProjectModel projectModel = (ProjectModel) event.getSource();
            invalidate(projectModel.getProjectName());
            resetFileWatcher(projectModel);
        }

        private void resetFileWatcher(final ProjectModel projectModel) {
            stopWatchingRuleSetFiles(projectModel);
            startWatchingRuleSetFiles(projectModel);
        }

    }

    /**
     * Invalidates the respective cache entry when a rule set file is changed.
     */
    private final class RuleSetFileListener implements FileChangedListener {

        private final String projectName;

        public RuleSetFileListener(final ProjectModel projectModel) {
            projectName = projectModel.getProjectName();
        }

        @Override
        public void fileChanged(final Path file) {
            invalidate(projectName);
        }

    }

}
