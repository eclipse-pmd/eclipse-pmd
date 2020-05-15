package ch.acanda.eclipse.pmd.swtbot.bot;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

import ch.acanda.eclipse.pmd.swtbot.SWTBotID;

/**
 * The {@link FileSelectionDialogBot} provides easy access to the
 * {@link ch.acanda.eclipse.pmd.ui.dialog.FileSelectionDialog FileSelectionDialog}'s widgets.
 *
 * @author Philip Graf
 */
public final class FileSelectionDialogBot extends DialogBot {

    private FileSelectionDialogBot(final Shell shell) {
        super(shell);
    }

    public static FileSelectionDialogBot getActive() {
        return new FileSelectionDialogBot(new SWTWorkbenchBot().shellWithId(SWTBotID.FILE_SELECTION_DIALOG.name()).widget);
    }

    public void select(final String... items) {
        SWTBotTreeItem treeItem = bot().tree().getTreeItem(items[0]);
        for (int i = 1; i < items.length; i++) {
            treeItem.expand();
            treeItem = treeItem.getNode(items[i]);
        }
        treeItem.select();
    }
}
