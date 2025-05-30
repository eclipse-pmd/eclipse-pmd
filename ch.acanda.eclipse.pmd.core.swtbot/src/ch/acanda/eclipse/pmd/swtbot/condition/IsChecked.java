package ch.acanda.eclipse.pmd.swtbot.condition;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;

/**
 * This condition tests if a table item is checked.
 */
public class IsChecked extends DefaultCondition {

    private final SWTBotTableItem tableItem;

    public IsChecked(final SWTBotTableItem tableItem) {
        this.tableItem = tableItem;
    }

    @Override
    public boolean test() throws Exception {
        return tableItem.isChecked();
    }

    @Override
    public String getFailureMessage() {
        return "TableItem " + tableItem.getText() + " is not checked";
    }

}
