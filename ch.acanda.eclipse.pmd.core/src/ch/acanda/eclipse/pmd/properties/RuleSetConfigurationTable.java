package ch.acanda.eclipse.pmd.properties;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.databinding.viewers.IViewerObservableList;
import org.eclipse.jface.databinding.viewers.IViewerObservableSet;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ch.acanda.eclipse.pmd.properties.PMDPropertyPageViewModel.RuleSetViewModel;
import ch.acanda.eclipse.pmd.swtbot.SWTBotID;

/**
 * A composite containing a single checkbox table viewer showing the rule set configurations.
 */
final class RuleSetConfigurationTable extends Composite {

    private final CheckboxTableViewer tableViewer;
    private final Table table;
    private final PMDPropertyPageViewModel model;

    public RuleSetConfigurationTable(final Composite parent, final PMDPropertyPageViewModel model) {
        super(parent, SWT.NONE);
        this.model = model;

        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
        final TableColumnLayout tableColumnLayout = new TableColumnLayout();
        setLayout(tableColumnLayout);

        tableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
        table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        ColumnViewerToolTipSupport.enableFor(tableViewer, ToolTip.NO_RECREATE);
        SWTBotID.set(table, SWTBotID.RULESETS);

        final TableViewerColumn nameViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        final TableColumn nameColumn = nameViewerColumn.getColumn();
        tableColumnLayout.setColumnData(nameColumn, new ColumnWeightData(1, 50, true));
        nameColumn.setText("Name");
        nameViewerColumn.setLabelProvider(new NameLabelProvider(model));

        final TableViewerColumn typeViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        final TableColumn typeColumn = typeViewerColumn.getColumn();
        tableColumnLayout.setColumnData(typeColumn, new ColumnPixelData(75, true, true));
        typeColumn.setText("Type");
        typeViewerColumn.setLabelProvider(new TypeLabelProvider(model));

        final TableViewerColumn locationViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        final TableColumn locationColumn = locationViewerColumn.getColumn();
        tableColumnLayout.setColumnData(locationColumn, new ColumnWeightData(2, 50, true));
        locationColumn.setText("Location");
        locationViewerColumn.setLabelProvider(new LocationLabelProvider(model));

        initDataBindings();
        initListeners();
    }

    private void initDataBindings() {
        final DataBindingContext bindingContext = new DataBindingContext();
        tableViewer.setContentProvider(new ObservableListContentProvider<>());
        tableViewer.setInput(BeanProperties.list(PMDPropertyPageViewModel.class, "ruleSets", RuleSetViewModel.class).observe(model));

        final ISWTObservableValue<Boolean> enabledView = WidgetProperties.enabled().observe(table);
        final IObservableValue<Boolean> enbaledModel =
                BeanProperties.value(PMDPropertyPageViewModel.class, "PMDEnabled", boolean.class).observe(model);
        bindingContext.bindValue(enabledView, enbaledModel);

        final IViewerObservableList<RuleSetViewModel> selectionView =
                ViewerProperties.multipleSelection(RuleSetViewModel.class).observe(tableViewer);
        final IObservableList<RuleSetViewModel> selectionModel =
                BeanProperties.list(PMDPropertyPageViewModel.class, "selectedRuleSets", RuleSetViewModel.class).observe(model);
        bindingContext.bindList(selectionView, selectionModel);

        final IViewerObservableSet<RuleSetViewModel> activeView =
                ViewerProperties.checkedElements(RuleSetViewModel.class).observe((Viewer) tableViewer);
        final IObservableSet<RuleSetViewModel> activeModel =
                BeanProperties.set(PMDPropertyPageViewModel.class, "activeRuleSets", RuleSetViewModel.class).observe(model);
        bindingContext.bindSet(activeView, activeModel);
    }

    private void initListeners() {
        model.addPropertyChangeListener(PMDPropertyPageViewModel.ACTIVE_RULE_SETS, evt -> tableViewer.refresh(true));
    }

}
