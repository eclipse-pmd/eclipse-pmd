package ch.acanda.eclipse.pmd.wizard;

import org.eclipse.jface.wizard.IWizardPage;

import ch.acanda.eclipse.pmd.domain.RuleSetModel;

public interface RuleSetWizardPage extends IWizardPage {

    RuleSetModel getRuleSet();

}
