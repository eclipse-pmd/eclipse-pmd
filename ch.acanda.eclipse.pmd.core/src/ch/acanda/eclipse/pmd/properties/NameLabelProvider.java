package ch.acanda.eclipse.pmd.properties;

import org.eclipse.swt.graphics.Image;

import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

/**
 * Provides the label, image and tool tip for the name column.
 */
final class NameLabelProvider extends RuleSetConfigurationLabelProvider {

    protected NameLabelProvider(final PMDPropertyPageViewModel model) {
        super(model);
    }

    @Override
    protected String getText(final RuleSetViewModel ruleSet) {
        return ruleSet.getName();
    }

    @Override
    public Image getImage(final Object element) {
        return getImage(toRuleSet(element));
    }

    @Override
    public String getToolTipText(final Object element) {
        final RuleSetViewModel ruleSet = toRuleSet(element);
        if (!ruleSet.isLocationValid()) {
            return getErrorMessage(ruleSet);
        }
        return null;
    }

    @Override
    public Image getToolTipImage(final Object element) {
        return getImage(element);
    }

}
