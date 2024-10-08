package ch.acanda.eclipse.pmd.wizard;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import ch.acanda.eclipse.pmd.domain.RuleSetModel;
import ch.acanda.eclipse.pmd.swtbot.SWTBotID;
import ch.acanda.eclipse.pmd.ui.model.ValidationResult;
import ch.acanda.eclipse.pmd.ui.util.SelectionAdapter;
import net.sourceforge.pmd.lang.rule.Rule;

@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects" })
public class AddRuleSetConfigurationWizardPage extends WizardPage implements RuleSetWizardPage {

    private final AddRuleSetConfigurationController controller;

    private Text location;
    private TableViewer tableViewer;
    private Text name;
    private Button browse;

    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public AddRuleSetConfigurationWizardPage(final AddRuleSetConfigurationController controller) {
        super("addFileSystemRuleSetConfigurationWizardPage");
        this.controller = controller;
        setTitle("Add Rule Set Configuration");
        setDescription("Click 'Finish' to add the rule set configuration");
        setPageComplete(false);
        controller.getModel().addValidationChangeListener(evt -> {
            final ValidationResult validationResult = (ValidationResult) evt.getNewValue();
            setErrorMessage(validationResult.getFirstErrorMessage());
            setPageComplete(!validationResult.hasErrors());
        });
        controller.getModel().reset();
    }

    @Override
    public void createControl(final Composite parent) {
        final Composite container = new Composite(parent, SWT.NULL);
        setControl(container);
        container.setLayout(new GridLayout(3, false));

        final Label lblLocation = new Label(container, SWT.NONE);
        lblLocation.setText("Location:");

        location = new Text(container, SWT.BORDER);
        location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        SWTBotID.set(location, SWTBotID.LOCATION);

        browse = new Button(container, SWT.NONE);
        browse.setText("Browse...");
        SWTBotID.set(browse, SWTBotID.BROWSE);
        browse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                controller.browse(((Control) e.widget).getShell());
            }
        });

        final Label lblName = new Label(container, SWT.NONE);
        lblName.setText("Name:");

        name = new Text(container, SWT.BORDER);
        name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        SWTBotID.set(name, SWTBotID.NAME);

        // This button is only here to make this row the same height as the previous row.
        // Without this button the distance between the text of this row and the text of
        // the previous row is much larger than the distance between the text of this row
        // and the table of the next row.
        final Button button = new Button(container, SWT.NONE);
        button.setEnabled(false);
        button.setVisible(false);

        final Label lblRules = new Label(container, SWT.NONE);
        final GridData lblRulesGridData = new GridData(SWT.LEFT, SWT.TOP, false, false);
        lblRulesGridData.verticalIndent = 3;
        lblRules.setLayoutData(lblRulesGridData);
        lblRules.setText("Rules:");

        final Composite tableComposite = new Composite(container, SWT.NONE);
        tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        final TableColumnLayout tableCompositeTableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableCompositeTableColumnLayout);

        tableViewer = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
        final Table table = tableViewer.getTable();
        table.setLinesVisible(true);
        SWTBotID.set(table, SWTBotID.RULES);

        final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        final TableColumn tblclmnName = tableViewerColumn.getColumn();
        tableCompositeTableColumnLayout.setColumnData(tblclmnName, new ColumnWeightData(1, 200, false));
        tblclmnName.setText("Name");
        new Label(container, SWT.NONE);
        new Label(container, SWT.NONE);
        new Label(container, SWT.NONE);
        new Label(container, SWT.NONE);

        initDataBindings();
    }

    @Override
    public RuleSetModel getRuleSet() {
        return controller.createRuleSetModel();
    }

    private DataBindingContext initDataBindings() {
        final DataBindingContext bindingContext = new DataBindingContext();
        final AddRuleSetConfigurationModel model = controller.getModel();

        final ISWTObservableValue<String> locationView = WidgetProperties.text(SWT.Modify).observeDelayed(100, location);
        final IObservableValue<String> locationModel =
                BeanProperties.value(AddRuleSetConfigurationModel.class, "location", String.class).observe(model);
        bindingContext.bindValue(locationView, locationModel);

        final ObservableListContentProvider<Rule> rulesContentProvider = new ObservableListContentProvider<>();
        final IObservableMap<Rule, String> ruleNamesModel =
                PojoProperties.value(Rule.class, "name", String.class).observeDetail(rulesContentProvider.getKnownElements());
        tableViewer.setLabelProvider(new ObservableMapLabelProvider(ruleNamesModel));
        tableViewer.setContentProvider(rulesContentProvider);
        final IObservableList<Rule> rulesModel =
                BeanProperties.list(AddRuleSetConfigurationModel.class, "rules", Rule.class).observe(model);
        tableViewer.setInput(rulesModel);

        final ISWTObservableValue<String> nameView = WidgetProperties.text(SWT.Modify).observeDelayed(100, name);
        final IObservableValue<String> nameModel =
                BeanProperties.value(AddRuleSetConfigurationModel.class, "name", String.class).observe(model);
        bindingContext.bindValue(nameView, nameModel);

        final ISWTObservableValue<Boolean> browseVisibleView = WidgetProperties.visible().observe(browse);
        final IObservableValue<Boolean> browseEnabledModel =
                BeanProperties.value(AddRuleSetConfigurationModel.class, "browseEnabled", boolean.class).observe(model);
        bindingContext.bindValue(browseVisibleView, browseEnabledModel);

        return bindingContext;
    }
}
