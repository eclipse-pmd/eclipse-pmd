// =====================================================================
//
// Copyright (C) 2012 - 2020, Philip Graf
//
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// =====================================================================

package ch.acanda.eclipse.pmd.properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.junit.Test;

import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

/**
 * Unit tests for {@link PMDPropertyPageController}.
 *
 * @author Philip Graf
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

        assertEquals("ruleSets", "[A, D]", toNameString(model.getRuleSets()));
        assertEquals("activeRuleSets", "[A]", toNameString(model.getActiveRuleSets()));
        assertEquals("selectedRuleSets", "[]", toNameString(model.getSelectedRuleSets()));
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

        assertEquals("ruleSets", "[A, B, C, D]", toNameString(model.getRuleSets()));
        assertEquals("activeRuleSets", "[A, B]", toNameString(model.getActiveRuleSets()));
        assertEquals("selectedRuleSets", "[]", toNameString(model.getSelectedRuleSets()));
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
