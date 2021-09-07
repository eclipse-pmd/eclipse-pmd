package ch.acanda.eclipse.pmd.cache;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.google.common.cache.CacheLoader;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.builder.LocationResolver;
import ch.acanda.eclipse.pmd.domain.ProjectModel;
import ch.acanda.eclipse.pmd.repository.ProjectModelRepository;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetLoadException;
import net.sourceforge.pmd.RuleSetLoader;

public class RuleSetsCacheLoader extends CacheLoader<String, List<RuleSet>> {

    private final ProjectModelRepository repository = new ProjectModelRepository();

    @Override
    public List<RuleSet> load(final String projectName) {
        PMDPlugin.getDefault().info("RuleSetsCache: loading rule sets for project " + projectName);
        try {
            final RuleSetLoader loader = new RuleSetLoader();
            final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
            final ProjectModel projectModel = repository.load(projectName).orElseGet(() -> new ProjectModel(projectName));
            return projectModel
                    .getRuleSets()
                    .stream()
                    .map(model -> LocationResolver.resolveIfExists(model.getLocation(), project))
                    .flatMap(Optional::stream)
                    .map(loader::loadFromResource)
                    .collect(Collectors.toList());
        } catch (final RuleSetLoadException e) {
            PMDPlugin.getDefault().error("Cannot load rule sets for project " + projectName, e);
            return List.of();
        }
    }

}
