package ch.acanda.eclipse.pmd.properties;

import static ch.acanda.eclipse.pmd.properties.PMDPropertyPageModelTransformer.toViewModels;
import static com.google.common.collect.Iterables.elementsEqual;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.core.resources.IProject;

import ch.acanda.eclipse.pmd.domain.RuleSetModel;
import ch.acanda.eclipse.pmd.ui.model.ValidationResult;
import ch.acanda.eclipse.pmd.ui.model.ViewModel;

/**
 * View model for the {@link PMDPropertyPage}.
 *
 * @author Philip Graf
 */
final class PMDPropertyPageViewModel extends ViewModel {

    public static final String RULE_SETS = "ruleSets";
    public static final String ACTIVE_RULE_SETS = "activeRuleSets";

    /**
     * All available rule sets of the entire workspace.
     */
    private List<? extends RuleSetViewModel> ruleSets = List.of();

    /**
     * The rule sets currently selected.
     */
    private List<? extends RuleSetViewModel> selectedRuleSets = List.of();

    /**
     * The rule sets currently activated, i.e. with a checked checkbox.
     */
    private Set<? extends RuleSetViewModel> activeRuleSets = Set.of();

    private boolean isPMDEnabled;
    private boolean initialIsPMDEnabled;
    private Iterable<? extends RuleSetViewModel> initialActiveRuleSets;

    public boolean isPMDEnabled() {
        return isPMDEnabled;
    }

    public void setPMDEnabled(final boolean isPMDEnabled) {
        setProperty("PMDEnabled", this.isPMDEnabled, this.isPMDEnabled = isPMDEnabled);
    }

    public List<? extends RuleSetViewModel> getRuleSets() {
        return ruleSets;
    }

    public void setRuleSets(final List<? extends RuleSetViewModel> ruleSets) {
        setProperty(RULE_SETS, this.ruleSets, this.ruleSets = List.copyOf(ruleSets));
    }

    /**
     * Sets the initial state, i.e. the state of the view model before any changes were made. This state is used by
     * {@link #updateDirty()} so it must be set before any properties of the view model are changed.
     */
    public void setInitialState(final boolean isPmdEnabled, final SortedSet<RuleSetModel> ruleSets, final IProject project) {
        initialIsPMDEnabled = isPMDEnabled;
        initialActiveRuleSets = toViewModels(ruleSets, project);
    }

    @Override
    protected boolean updateDirty() {
        boolean isClean = initialIsPMDEnabled == isPMDEnabled();
        isClean = isClean && elementsEqual(initialActiveRuleSets, getActiveRuleSets());
        return !isClean;
    }

    public void addRuleSet(final RuleSetViewModel viewModel) {
        final List<RuleSetViewModel> newConfigurations = new ArrayList<>(ruleSets);
        newConfigurations.add(viewModel);
        setRuleSets(newConfigurations);
    }

    @Override
    protected Set<String> createValidatedPropertiesSet() {
        return Set.of();
    }

    @Override
    protected void validate(final String propertyName, final ValidationResult result) {
        // nothing to validate
    }

    public void setSelectedRuleSets(final List<? extends RuleSetViewModel> ruleSets) {
        setProperty("selectedRuleSets", selectedRuleSets, selectedRuleSets = List.copyOf(ruleSets));
    }

    public List<? extends RuleSetViewModel> getSelectedRuleSets() {
        return selectedRuleSets;
    }

    public void setActiveRuleSets(final Set<? extends RuleSetViewModel> ruleSets) {
        setProperty(ACTIVE_RULE_SETS, activeRuleSets, activeRuleSets = Set.copyOf(ruleSets));
    }

    public Set<? extends RuleSetViewModel> getActiveRuleSets() {
        return activeRuleSets;
    }

    static final class RuleSetViewModel {

        private final String name;
        private final String type;
        private final String location;
        private final boolean isLocationValid;
        private final String resolvedPath;

        public RuleSetViewModel(final String name, final String type, final String location, final boolean isLocationValid,
                final String resolvedPath) {
            this.name = name;
            this.type = type;
            this.location = location;
            this.isLocationValid = isLocationValid;
            this.resolvedPath = resolvedPath;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getLocation() {
            return location;
        }

        public boolean isLocationValid() {
            return isLocationValid;
        }

        public String getResolvedPath() {
            return resolvedPath;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (location == null ? 0 : location.hashCode());
            result = prime * result + (name == null ? 0 : name.hashCode());
            result = prime * result + (type == null ? 0 : type.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final RuleSetViewModel other = (RuleSetViewModel) obj;
            if (location == null) {
                if (other.location != null) {
                    return false;
                }
            } else if (!location.equals(other.location)) {
                return false;
            }
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }
            if (type == null) {
                if (other.type != null) {
                    return false;
                }
            } else if (!type.equals(other.type)) {
                return false;
            }
            return true;
        }

    }

}
