package ch.acanda.eclipse.pmd.properties;

import static ch.acanda.eclipse.pmd.domain.LocationContext.FILE_SYSTEM;
import static ch.acanda.eclipse.pmd.domain.LocationContext.PROJECT;
import static ch.acanda.eclipse.pmd.domain.LocationContext.REMOTE;
import static ch.acanda.eclipse.pmd.domain.LocationContext.WORKSPACE;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;

import ch.acanda.eclipse.pmd.builder.LocationResolver;
import ch.acanda.eclipse.pmd.cache.RuleSetsCacheLoader;
import ch.acanda.eclipse.pmd.domain.Location;
import ch.acanda.eclipse.pmd.domain.LocationContext;
import ch.acanda.eclipse.pmd.domain.RuleSetModel;
import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;
import net.sourceforge.pmd.lang.rule.RuleSetLoadException;

/**
 * Transforms the domain model to and from the PMD property page's view model.
 */
public final class PMDPropertyPageModelTransformer {

    /**
     * Maps a location context to a label of a rule set type and vice versa. The label is used as the value shown in the
     * "Type" column of the table in the PMD property page.
     */
    private static final Map<LocationContext, String> CONTEXT_TYPE_MAP = Map.of(
            WORKSPACE, "Workspace",
            PROJECT, "Project",
            FILE_SYSTEM, "File System",
            REMOTE, "Remote");

    private static final Map<String, LocationContext> TYPE_CONTEXT_MAP = CONTEXT_TYPE_MAP.entrySet().stream()
            .collect(Collectors.toMap(Entry::getValue, Entry::getKey));

    private PMDPropertyPageModelTransformer() {
        // hide constructor of utility class
    }

    @SuppressWarnings("java:S1452")
    public static Set<? extends RuleSetViewModel> toViewModels(final SortedSet<RuleSetModel> domainModels, final IProject project) {
        return domainModels.stream().map(model -> toViewModel(model, project)).collect(Collectors.toSet());
    }

    public static RuleSetViewModel toViewModel(final RuleSetModel ruleSetModel, final IProject project) {
        final String name = ruleSetModel.getName();
        final String type = CONTEXT_TYPE_MAP.get(ruleSetModel.getLocation().getContext());
        final String location = ruleSetModel.getLocation().getPath();
        final Optional<String> locationOption = LocationResolver.resolveIfExists(ruleSetModel.getLocation(), project);
        final boolean isValidLocation = locationOption.isPresent();
        final String resolvedLocation = locationOption.orElse(null);
        final String ruleSetErrorMessage = locationOption.map(PMDPropertyPageModelTransformer::getRuleSetErrorMessage).orElse(null);
        return new RuleSetViewModel(name, type, location, isValidLocation, resolvedLocation, ruleSetErrorMessage);
    }

    private static String getRuleSetErrorMessage(final String resolvedLocation) {
        try {
            RuleSetsCacheLoader.loadRuleSet(resolvedLocation);
            return null;
        } catch (final RuleSetLoadException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @SuppressWarnings("java:S1452")
    public static List<? extends RuleSetModel> toDomainModels(final Collection<? extends RuleSetViewModel> viewModels) {
        return viewModels.stream().map(PMDPropertyPageModelTransformer::toDomainModel).toList();
    }

    public static RuleSetModel toDomainModel(final RuleSetViewModel viewModel) {
        final String name = viewModel.getName();
        final String path = viewModel.getLocation();
        final LocationContext context = TYPE_CONTEXT_MAP.get(viewModel.getType());
        return new RuleSetModel(name, new Location(path, context));
    }

}
