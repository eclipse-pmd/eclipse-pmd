package ch.acanda.eclipse.pmd.swtbot.bot;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

public final class PMDPreferenceDialogBot extends DialogBot {

    private PMDPreferenceDialogBot(final Shell shell) {
        super(shell);
    }

    public static PMDPreferenceDialogBot open() {
        final SWTWorkbenchBot bot = new SWTWorkbenchBot();
        bot.menu("Window").menu("Preferences").click();
        final PMDPreferenceDialogBot preferenceBot = new PMDPreferenceDialogBot(bot.shell("Preferences").widget);
        preferenceBot.bot().tree().getTreeItem("PMD").select();
        return preferenceBot;
    }

}
