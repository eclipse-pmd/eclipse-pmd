package ch.acanda.eclipse.pmd.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.junit.jupiter.api.Test;

import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

/**
 * Unit tests for {@link PMDPropertyPageController}.
 */
public class PMDPropertyPageControllerTest {

    /**
     * Verifies that {@link PMDPropertyPageController#removeSelectedConfigurations()} updates the view model correctly.
     */
    @Test
    public void removeSelectedConfigurations() {
        final PMDPropertyPageController controller = new PMDPropertyPageController();
        final PMDPropertyPageViewModel model = createModel(controller, 1, 3);

        controller.removeSelectedConfigurations();

        assertEquals("[A, D]", toNameString(model.getRuleSets()), "ruleSets");
        assertEquals("[A]", toNameString(model.getActiveRuleSets()), "activeRuleSets");
        assertEquals("[]", toNameString(model.getSelectedRuleSets()), "selectedRuleSets");
    }

    /**
     * Verifies that {@link PMDPropertyPageController#removeSelectedConfigurations()} updates the view model correctly
     * when none of the rule sets are selected.
     */
    @Test
    public void removeSelectedConfigurationsWithoutSelection() {
        final PMDPropertyPageController controller = new PMDPropertyPageController();
        final PMDPropertyPageViewModel model = createModel(controller, 0, 0);

        controller.removeSelectedConfigurations();

        assertEquals("[A, B, C, D]", toNameString(model.getRuleSets()), "ruleSets");
        assertEquals("[A, B]", toNameString(model.getActiveRuleSets()), "activeRuleSets");
        assertEquals("[]", toNameString(model.getSelectedRuleSets()), "selectedRuleSets");
    }

    private static PMDPropertyPageViewModel createModel(final PMDPropertyPageController controller, final int from, final int to) {
        final PMDPropertyPageViewModel model = controller.getModel();
        final IProject project = mock(IProject.class);
        model.setInitialState(true, Collections.emptySortedSet(), project);
        final List<? extends RuleSetViewModel> ruleSets = createRuleSets();
        model.setRuleSets(ruleSets);
        model.setActiveRuleSets(Set.copyOf(ruleSets.subList(0, 2)));
        model.setSelectedRuleSets(ruleSets.subList(from, to));
        return model;
    }

    /**
     * Returns a string representation of the provided rule set containing its element's names.
     */
    private static String toNameString(final Collection<? extends RuleSetViewModel> ruleSets) {
        return ruleSets.stream().map(RuleSetViewModel::getName).sorted().collect(Collectors.joining(", ", "[", "]"));
    }

    private static List<? extends RuleSetViewModel> createRuleSets() {
        return List.of(new RuleSetViewModel("A", "A-Type", "A-Location", true, "A-LocationToolTip"),
                new RuleSetViewModel("B", "B-Type", "B-Location", false, "B-LocationToolTip"),
                new RuleSetViewModel("C", "C-Type", "C-Location", true, "C-LocationToolTip"),
                new RuleSetViewModel("D", "D-Type", "D-Location", false, "D-LocationToolTip"));
    }

}
