package ch.acanda.eclipse.pmd.properties;

import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

/**
 * Provides the label and tool tip for the location column.
 */
final class LocationLabelProvider extends RuleSetConfigurationLabelProvider {

    protected LocationLabelProvider(final PMDPropertyPageViewModel model) {
        super(model);
    }

    @Override
    protected String getText(final RuleSetViewModel ruleSet) {
        return ruleSet.getLocation();
    }

    @Override
    public String getToolTipText(final Object element) {
        final RuleSetViewModel ruleSet = toRuleSet(element);
        if (ruleSet.isValid()) {
            return ruleSet.getResolvedPath();
        }
        return getErrorMessage(ruleSet);
    }

}
