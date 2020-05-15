package ch.acanda.eclipse.pmd.properties;

import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

/**
 * Provides the label for the type column.
 *
 * @author Philip Graf
 */
final class TypeLabelProvider extends RuleSetConfigurationLabelProvider {

    protected TypeLabelProvider(final PMDPropertyPageViewModel model) {
        super(model);
    }

    @Override
    protected String getText(final RuleSetViewModel ruleSet) {
        return ruleSet.getType();
    }

}
