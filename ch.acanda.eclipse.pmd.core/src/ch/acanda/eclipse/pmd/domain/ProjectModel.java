package ch.acanda.eclipse.pmd.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ProjectModel extends DomainModel {

    public static final String RULESETS_PROPERTY = "ruleSets";
    public static final String PMDENABLED_PROPERTY = "isPMDEnabled";

    public static final Comparator<RuleSetModel> RULE_SET_COMPARATOR =
            Comparator.comparing((final RuleSetModel rs) -> rs.getLocation().getContext())
                    .thenComparing(RuleSetModel::getName)
                    .thenComparing(rs -> rs.getLocation().getPath());

    private final String projectName;

    private boolean isPMDEnabled;
    private SortedSet<RuleSetModel> ruleSets = Collections.emptySortedSet();

    /**
     * Creates a new project model without any rule sets and where PMD is disabled.
     *
     * @param projectName The name of the project, see {@link org.eclipse.core.resources.IProject#getName()}.
     */
    public ProjectModel(final String projectName) {
        this.projectName = checkNotNull(projectName, "The argument name must be a valid project name.");
    }

    public String getProjectName() {
        return projectName;
    }

    @SuppressWarnings("java:S1121")
    public void setPMDEnabled(final boolean isPMDEnabled) {
        setProperty(PMDENABLED_PROPERTY, this.isPMDEnabled, this.isPMDEnabled = isPMDEnabled);
    }

    public boolean isPMDEnabled() {
        return isPMDEnabled;
    }

    @SuppressWarnings("java:S1121")
    public void setRuleSets(final Iterable<? extends RuleSetModel> ruleSets) {
        final SortedSet<RuleSetModel> set = new TreeSet<>(RULE_SET_COMPARATOR);
        ruleSets.forEach(set::add);
        setProperty(RULESETS_PROPERTY, this.ruleSets, this.ruleSets = set);
    }

    public SortedSet<RuleSetModel> getRuleSets() {
        return ruleSets;
    }

}
