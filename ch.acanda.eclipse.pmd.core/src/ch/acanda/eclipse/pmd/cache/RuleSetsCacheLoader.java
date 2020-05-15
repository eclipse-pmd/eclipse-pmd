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
import net.sourceforge.pmd.RuleSetNotFoundException;
import net.sourceforge.pmd.RuleSetReferenceId;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.RulesetsFactoryUtils;

/**
 * @author Philip Graf
 */
public class RuleSetsCacheLoader extends CacheLoader<String, RuleSets> {

    private final ProjectModelRepository repository = new ProjectModelRepository();

    @Override
    public RuleSets load(final String projectName) {
        PMDPlugin.getDefault().info("RuleSetsCache: loading rule sets for project " + projectName);
        try {
            final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
            final ProjectModel projectModel = repository.load(projectName).orElseGet(() -> new ProjectModel(projectName));
            final List<RuleSetReferenceId> ruleSetIds = projectModel
                    .getRuleSets()
                    .stream()
                    .map(model -> LocationResolver.resolveIfExists(model.getLocation(), project))
                    .flatMap(Optional::stream)
                    .map(RuleSetReferenceId::new)
                    .collect(Collectors.toList());
            return RulesetsFactoryUtils.defaultFactory().createRuleSets(ruleSetIds);
        } catch (final RuleSetNotFoundException e) {
            PMDPlugin.getDefault().error("Cannot load rule sets for project " + projectName, e);
            return new RuleSets();
        }
    }

}
