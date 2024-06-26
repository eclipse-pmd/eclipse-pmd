package ch.acanda.eclipse.pmd.swtbot.tests;

import static ch.acanda.eclipse.pmd.swtbot.condition.Conditions.isPerspectiveActive;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class GUITestCase {

    private static final String INTRO_VIEW_ID = "org.eclipse.ui.internal.introview";

    private final SWTWorkbenchBot bot;

    protected GUITestCase() {
        bot = new SWTWorkbenchBot();
    }

    @BeforeAll
    public static void initSWTBotPreferencesAndOpenJavaPerspective() {
        SWTBotPreferences.TIMEOUT = 10_000;
        final SWTWorkbenchBot workbenchBot = new SWTWorkbenchBot();
        closeWelcomeView(workbenchBot);
        openJavaPerspective(workbenchBot);
    }

    @AfterAll
    public static void resetWorkbench() {
        final SWTWorkbenchBot workbenchBot = new SWTWorkbenchBot();
        workbenchBot.resetWorkbench();
    }

    @AfterEach
    public void closeAllDialogs() {
        bot.closeAllShells();
    }

    private static void closeWelcomeView(final SWTWorkbenchBot workbenchBot) {
        for (final SWTBotView view : workbenchBot.views()) {
            if (INTRO_VIEW_ID.equals(view.getReference().getId())) {
                view.close();
            }
        }
    }

    private static void openJavaPerspective(final SWTWorkbenchBot workbenchBot) {
        final SWTBotPerspective javaPerspective = workbenchBot.perspectiveById("org.eclipse.jdt.ui.JavaPerspective");
        if (!javaPerspective.isActive()) {
            workbenchBot.menu("Window").menu("Perspective").menu("Open Perspective").menu("Java").click();
            workbenchBot.waitUntil(isPerspectiveActive(javaPerspective));
        }
    }

    protected SWTWorkbenchBot bot() {
        return bot;
    }

}
