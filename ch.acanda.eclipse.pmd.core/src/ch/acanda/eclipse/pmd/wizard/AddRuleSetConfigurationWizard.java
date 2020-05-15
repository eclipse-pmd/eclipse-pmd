package ch.acanda.eclipse.pmd.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

import ch.acanda.eclipse.pmd.domain.RuleSetModel;

public class AddRuleSetConfigurationWizard extends Wizard {

    private final AddRuleSetConfigurationController controller;
    private RuleSetModel ruleSetModel;
    RuleSetWizardPage ruleSetConfigurationWizardPage;

    public AddRuleSetConfigurationWizard(final IProject project) {
        controller = new AddRuleSetConfigurationController(project);
        setWindowTitle("Add Rule Set Configuration");
        setNeedsProgressMonitor(false);
    }

    @Override
    public boolean performFinish() {
        ruleSetModel = controller.createRuleSetModel();
        return true;
    }

    public RuleSetModel getRuleSetModel() {
        return ruleSetModel;
    }

    @Override
    public void addPages() {
        addPage(new SelectConfigurationTypeWizardPage(controller));
        addPage(new AddRuleSetConfigurationWizardPage(controller));
    }

}
