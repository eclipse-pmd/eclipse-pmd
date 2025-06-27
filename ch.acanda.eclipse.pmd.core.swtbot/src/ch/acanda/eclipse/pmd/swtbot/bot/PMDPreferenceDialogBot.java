package ch.acanda.eclipse.pmd.swtbot.bot;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.anyOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;

import java.util.List;

import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.hamcrest.Matcher;

public final class PMDPreferenceDialogBot extends DialogBot {

    // Eclipse 2025-03 and earlier use "Preferences", 2025-06 uses "Preferences..."
    private static final Matcher<MenuItem> PREFERENCES =
            anyOf(List.of(withMnemonic("Preferences"), withMnemonic("Preferences...")));

    private PMDPreferenceDialogBot(final Shell shell) {
        super(shell);
    }

    public static PMDPreferenceDialogBot open() {
        final SWTWorkbenchBot bot = new SWTWorkbenchBot();
        bot.menu("Window").menu(PREFERENCES, false, 0).click();
        final PMDPreferenceDialogBot preferenceBot = new PMDPreferenceDialogBot(bot.shell("Preferences").widget);
        preferenceBot.bot().tree().getTreeItem("PMD").select();
        return preferenceBot;
    }

}
