package ch.acanda.eclipse.pmd.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
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
import net.sourceforge.pmd.Rule;

@SuppressWarnings("PMD.ExcessiveImports")
public class AddRuleSetConfigurationWizardPage extends WizardPage implements RuleSetWizardPage {

    private final AddRuleSetConfigurationController controller;

    private Text location;
    private TableViewer tableViewer;
    private Text name;
    private Button browse;

    public AddRuleSetConfigurationWizardPage(final AddRuleSetConfigurationController controller) {
        super("addFileSystemRuleSetConfigurationWizardPage");
        this.controller = controller;
        setTitle("Add Rule Set Configuration");
        setDescription("Click 'Finish' to add the rule set configuration");
        setPageComplete(false);
        controller.getModel().addValidationChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                final ValidationResult validationResult = (ValidationResult) evt.getNewValue();
                setErrorMessage(validationResult.getFirstErrorMessage());
                setPageComplete(!validationResult.hasErrors());
            }
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
        //
        final IObservableValue locationView = SWTObservables.observeDelayedValue(200, SWTObservables.observeText(location, SWT.Modify));
        final IObservableValue locationModel = BeansObservables.observeValue(controller.getModel(), "location");
        bindingContext.bindValue(locationView, locationModel, null, null);
        //
        final ObservableListContentProvider rulesContentProvider = new ObservableListContentProvider();
        final IObservableMap ruleNamesModel = PojoObservables.observeMap(rulesContentProvider.getKnownElements(), Rule.class, "name");
        tableViewer.setLabelProvider(new ObservableMapLabelProvider(ruleNamesModel));
        tableViewer.setContentProvider(rulesContentProvider);
        //
        final IObservableList rulesModel = BeansObservables.observeList(Realm.getDefault(), controller.getModel(), "rules");
        tableViewer.setInput(rulesModel);
        //
        final IObservableValue nameView = SWTObservables.observeDelayedValue(100, SWTObservables.observeText(name, SWT.Modify));
        final IObservableValue nameModel = BeansObservables.observeValue(controller.getModel(), "name");
        bindingContext.bindValue(nameView, nameModel, null, null);
        //
        final IObservableValue browseVisibleView = WidgetProperties.visible().observe(browse);
        final IObservableValue browseEnabledModel = BeanProperties.value("browseEnabled").observe(controller.getModel());
        bindingContext.bindValue(browseVisibleView, browseEnabledModel, null, null);
        //
        return bindingContext;
    }
}
