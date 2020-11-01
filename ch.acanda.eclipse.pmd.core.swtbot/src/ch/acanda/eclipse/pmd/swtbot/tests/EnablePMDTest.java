package ch.acanda.eclipse.pmd.swtbot.tests;


import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ch.acanda.eclipse.pmd.swtbot.SWTBotID;
import ch.acanda.eclipse.pmd.swtbot.bot.PMDPropertyDialogBot;
import ch.acanda.eclipse.pmd.swtbot.client.JavaProjectClient;

/**
 * Tests if PMD can be enabled and disabled using the PMD property dialog.
 */
public final class EnablePMDTest extends GUITestCase {

    private static final String PROJECT_NAME = EnablePMDTest.class.getSimpleName();

    @BeforeAll
    public static void createJavaProject() {
        JavaProjectClient.createJavaProject(PROJECT_NAME);
    }

    @AfterAll
    public static void deleteJavaProject() {
        JavaProjectClient.deleteJavaProject(PROJECT_NAME);
    }

    @Test
    public void enablePMD() {
        PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME);
        SWTBotCheckBox enablePMDCheckBox = dialog.bot().checkBoxWithId(SWTBotID.ENABLE_PMD.name());
        assertFalse(enablePMDCheckBox.isChecked(), "PMD should be disabled by default");

        enablePMDCheckBox.select();
        dialog.ok().click();
        bot().waitUntil(shellCloses(dialog));

        dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME);
        enablePMDCheckBox = dialog.bot().checkBoxWithId(SWTBotID.ENABLE_PMD.name());
        assertTrue(enablePMDCheckBox.isChecked(), "PMD should be enabled");

        dialog.ok().click();
        bot().waitUntil(shellCloses(dialog));
    }

}
