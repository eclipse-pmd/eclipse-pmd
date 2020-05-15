package ch.acanda.eclipse.pmd.properties;

import java.text.MessageFormat;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;

/**
 * Base class for the table's column label providers.
 */
abstract class RuleSetConfigurationLabelProvider extends ColumnLabelProvider {

    private final PMDPropertyPageViewModel model;

    protected RuleSetConfigurationLabelProvider(final PMDPropertyPageViewModel model) {
        this.model = model;
    }

    @Override
    public String getText(final Object element) {
        return getText(toRuleSet(element));
    }

    protected abstract String getText(RuleSetViewModel ruleSet);

    protected RuleSetViewModel toRuleSet(final Object element) {
        return (RuleSetViewModel) element;
    }

    protected Image getImage(final RuleSetViewModel ruleSet) {
        if (!ruleSet.isLocationValid()) {
            final String key = isActive(ruleSet) ? ISharedImages.IMG_OBJS_ERROR_TSK : ISharedImages.IMG_OBJS_WARN_TSK;
            return PlatformUI.getWorkbench().getSharedImages().getImage(key);
        }
        return null;
    }

    protected boolean isActive(final RuleSetViewModel ruleSet) {
        return model.getActiveRuleSets().contains(ruleSet);
    }

    protected String getErrorMessage(final RuleSetViewModel ruleSet) {
        final String resolvedPath = ruleSet.getResolvedPath();
        if (resolvedPath != null) {
            final String template = "The file {0} does not exist";
            return MessageFormat.format(template, resolvedPath);
        }
        return "The file does not exist";
    }

}
