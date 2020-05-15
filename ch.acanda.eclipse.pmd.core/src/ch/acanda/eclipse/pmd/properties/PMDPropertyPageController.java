package ch.acanda.eclipse.pmd.properties;

import static ch.acanda.eclipse.pmd.properties.PMDPropertyPageModelTransformer.toDomainModels;
import static ch.acanda.eclipse.pmd.properties.PMDPropertyPageModelTransformer.toViewModel;
import static ch.acanda.eclipse.pmd.properties.PMDPropertyPageModelTransformer.toViewModels;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import ch.acanda.eclipse.pmd.PMDPlugin;
import ch.acanda.eclipse.pmd.builder.PMDNature;
import ch.acanda.eclipse.pmd.domain.ProjectModel;
import ch.acanda.eclipse.pmd.domain.RuleSetModel;
import ch.acanda.eclipse.pmd.domain.WorkspaceModel;
import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;
import ch.acanda.eclipse.pmd.repository.ProjectModelRepository;
import ch.acanda.eclipse.pmd.wizard.AddRuleSetConfigurationWizard;

/**
 * Controller for the PMD project property page.
 */
final class PMDPropertyPageController {

    private final PMDPropertyPageViewModel model;
    private ProjectModel projectModel;
    private IProject project;

    public PMDPropertyPageController() {
        model = new PMDPropertyPageViewModel();
    }

    public PMDPropertyPageViewModel getModel() {
        return model;
    }

    public void init(final IProject project) {
        this.project = project;
        final WorkspaceModel workspaceModel = PMDPlugin.getDefault().getWorkspaceModel();

        projectModel = workspaceModel.getOrCreateProject(project.getName());
        model.setInitialState(projectModel.isPMDEnabled(), projectModel.getRuleSets(), project);
        final TreeSet<RuleSetModel> ruleSetBuilder = new TreeSet<>(ProjectModel.RULE_SET_COMPARATOR);
        for (final ProjectModel projectModel : workspaceModel.getProjects()) {
            ruleSetBuilder.addAll(projectModel.getRuleSets());
        }
        model.setRuleSets(List.copyOf(toViewModels(ruleSetBuilder, project)));
        reset();
    }

    public void reset() {
        model.setActiveRuleSets(Set.copyOf(toViewModels(projectModel.getRuleSets(), project)));
        model.setSelectedRuleSets(List.of());
        model.setPMDEnabled(projectModel.isPMDEnabled());
    }

    public void save() {
        projectModel.setPMDEnabled(model.isPMDEnabled());
        projectModel.setRuleSets(toDomainModels(model.getActiveRuleSets()));

        final ProjectModelRepository projectModelRepository = new ProjectModelRepository();
        projectModelRepository.save(projectModel);

        try {
            if (model.isPMDEnabled()) {
                PMDNature.addTo(project);
            } else {
                PMDNature.removeFrom(project);
            }
        } catch (final CoreException e) {
            PMDPlugin.getDefault().error("Cannot change PMD nature of project " + project.getName(), e);
        }
    }

    public boolean isValid() {
        return true;
    }

    public void addRuleSetConfiguration(final Shell shell) {
        final AddRuleSetConfigurationWizard wizard = new AddRuleSetConfigurationWizard(project);
        final WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.setPageSize(300, SWT.DEFAULT);
        final int result = dialog.open();
        if (result == Window.OK && wizard.getRuleSetModel() != null) {
            final RuleSetViewModel viewModel = toViewModel(wizard.getRuleSetModel(), project);
            model.addRuleSet(viewModel);
            final HashSet<RuleSetViewModel> activeConfigs = new HashSet<>(model.getActiveRuleSets());
            activeConfigs.add(viewModel);
            model.setActiveRuleSets(activeConfigs);
        }
    }

    public void removeSelectedConfigurations() {
        final Predicate<RuleSetViewModel> notInSelection = m -> !model.getSelectedRuleSets().contains(m);
        model.setRuleSets(model.getRuleSets().stream().filter(notInSelection).collect(toList()));
        model.setActiveRuleSets(model.getActiveRuleSets().stream().filter(notInSelection).collect(toSet()));
        model.setSelectedRuleSets(List.of());
    }

}
