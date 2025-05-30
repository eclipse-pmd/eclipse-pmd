package ch.acanda.eclipse.pmd.swtbot.tests;

import static ch.acanda.eclipse.pmd.swtbot.condition.Conditions.isChecked;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.tableHasRows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ch.acanda.eclipse.pmd.swtbot.bot.AddRuleSetConfigurationWizardBot;
import ch.acanda.eclipse.pmd.swtbot.bot.FileSelectionDialogBot;
import ch.acanda.eclipse.pmd.swtbot.bot.PMDPropertyDialogBot;
import ch.acanda.eclipse.pmd.swtbot.client.JavaProjectClient;

/**
 * Tests the PMD rule set functionality of the PMD property dialog.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class PMDPropertyDialogTest extends GUITestCase {

    private static final String PROJECT_NAME_1 = PMDPropertyDialogTest.class.getSimpleName() + "1";
    private static final String PROJECT_NAME_2 = PMDPropertyDialogTest.class.getSimpleName() + "2";
    private static final String RULE_SET_FILE = "PMDRuleSetTest.xml";
    private static final String TEST_RULE_SET_NAME = "Test PMD Rule Set";
    private static final String FILE_SYSTEM_RULE_SET_NAME = "PMD Rules (File System)";
    private static final String WORKSPACE_RULE_SET_NAME = "PMD Rules (Workspace)";
    private static final String PROJECT_RULE_SET_NAME = "PMD Rules (Project)";
    private static final String REMOTE_RULE_SET_NAME = "PMD Rules (Remote)";
    private static final Path PMD_XML = Paths.get("pmd.xml");

    private static Path rules;

    @BeforeAll
    @SuppressWarnings("java:S5443")
    public static void createJavaProjects() throws IOException {
        JavaProjectClient.createJavaProject(PROJECT_NAME_1);

        final String content;
        try (InputStream in = PMDPropertyDialogTest.class.getResourceAsStream(RULE_SET_FILE)) {
            content = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        JavaProjectClient.createFileInProject(PROJECT_NAME_1, PMD_XML, content);

        JavaProjectClient.createJavaProject(PROJECT_NAME_2);

        rules = Files.createTempFile(PMDPropertyDialogTest.class.getSimpleName() + "-", ".xml");
        Files.writeString(rules, content);
    }

    @AfterAll
    public static void deleteJavaProjects() throws IOException {
        JavaProjectClient.deleteJavaProject(PROJECT_NAME_1);
        JavaProjectClient.deleteJavaProject(PROJECT_NAME_2);
        Files.deleteIfExists(rules);
    }

    @Test
    public void manageRuleSets() {
        enablePMD();
        addFileSystemRuleSetInFirstProject();
        activateTheSameFileSystemRuleSetInSecondProject();
        addWorkspaceRuleSetInFirstProject();
        addProjectRuleSetInFirstProject();
        addRemoteRuleSetInFirstProject();
        deactivateWorkspaceRuleSet();
        deactivateFileSystemRuleSet();
    }

    private void enablePMD() {
        final PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertFalse(dialog.enablePMD().isChecked(), "PMD should be disabled by default");
        assertFalse(dialog.addRuleSet().isEnabled(), "The button to add a new rule set should be disabled as long as PMD is disabled");
        dialog.enablePMD().select();
        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

    private void addFileSystemRuleSetInFirstProject() {
        final PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertTrue(dialog.enablePMD().isChecked(), "PMD should be enabled");
        assertTrue(dialog.addRuleSet().isEnabled(), "The button to add a new rule set should be enabled when PMD is enabled");

        dialog.addRuleSet().click();
        final AddRuleSetConfigurationWizardBot wizard = AddRuleSetConfigurationWizardBot.getActive();
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled as long as the name and location are missing");

        wizard.filesystem().click();
        wizard.next().click();
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled as long as the name and location are missing");
        assertTrue(wizard.isBrowseButtonVisible(), "The browse button should be visible for a file system rule set");

        wizard.location().setText(rules.toAbsolutePath().toString());
        wizard.bot().waitUntil(tableHasRows(wizard.rules(), 2));
        final String[] expectedNames = { "ExtendsObject", "PrimitiveWrapperInstantiation" };
        final String[] actualNames = wizard.ruleNames();
        assertEquals(TEST_RULE_SET_NAME, wizard.name().getText(), "The name of the ruleset should be loaded into the name text field");
        assertArrayEquals(expectedNames, actualNames, "Rules of the PMD ");
        wizard.waitUntilFinishIsEnabled("The finish button should be enabled if both a name and a location is available");

        wizard.name().setText("");
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled if the name is not available");

        wizard.name().setText(FILE_SYSTEM_RULE_SET_NAME);
        wizard.waitUntilFinishIsEnabled("The finish button should be enabled if the name is available");

        wizard.finish().click();
        wizard.bot().waitUntil(shellCloses(wizard));
        dialog.bot().waitUntil(tableHasRows(dialog.ruleSets(), 1));
        assertTrue(dialog.ruleSets().getTableItem(0).isChecked(), "The added rule set should be activated");
        assertEquals(FILE_SYSTEM_RULE_SET_NAME, dialog.ruleSets().cell(0, "Name"), "Name of the rule set");
        assertEquals("File System", dialog.ruleSets().cell(0, "Type"), "Type of the rule set");
        assertEquals(rules.toAbsolutePath().toString(), dialog.ruleSets().cell(0, "Location"), "Location of the rule set");

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

    private void activateTheSameFileSystemRuleSetInSecondProject() {
        PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_2);
        assertFalse(dialog.enablePMD().isChecked(), "PMD should be disabled by default");

        dialog.enablePMD().select();
        assertEquals(1, dialog.ruleSets().rowCount(), "The previously added rule set should als be available in the second project");
        assertFalse(dialog.ruleSets().getTableItem(0).isChecked(), "The available rule set should not be activated");
        assertEquals(FILE_SYSTEM_RULE_SET_NAME, dialog.ruleSets().cell(0, "Name"), "Name of the rule set");
        assertEquals("File System", dialog.ruleSets().cell(0, "Type"), "Type of the rule set");
        assertEquals(rules.toAbsolutePath().toString(), dialog.ruleSets().cell(0, "Location"), "Location of the rule set");

        dialog.ruleSets().getTableItem(0).check();
        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));

        dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_2);
        assertTrue(dialog.enablePMD().isChecked(), "PMD should be enabled");
        assertTrue(dialog.ruleSets().getTableItem(0).isChecked(), "The rule set should be activated");

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

    private void addWorkspaceRuleSetInFirstProject() {
        final PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertTrue(dialog.enablePMD().isChecked(), "PMD should be enabled");
        assertTrue(dialog.addRuleSet().isEnabled(), "The button to add a new rule set should be enabled when PMD is enabled");

        dialog.enablePMD().select();
        dialog.addRuleSet().click();
        final AddRuleSetConfigurationWizardBot wizard = AddRuleSetConfigurationWizardBot.getActive();
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled as long as the name is missing");

        wizard.workspace().click();
        wizard.next().click();
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled as long as the name is missing");

        assertTrue(wizard.isBrowseButtonVisible(), "The browse button should be visible for a workspace rule set");

        wizard.browse().click();
        final FileSelectionDialogBot fileSelectionDialog = FileSelectionDialogBot.getActive();
        fileSelectionDialog.select(PROJECT_NAME_1, PMD_XML.toString());
        fileSelectionDialog.ok().click();
        fileSelectionDialog.waitUntilClosed();
        final String workspaceRelativePath = PROJECT_NAME_1 + '/' + PMD_XML;
        assertEquals(wizard.location().getText(), workspaceRelativePath,
                "The location should contain the project name and the path to the rule set file");

        wizard.bot().waitUntil(tableHasRows(wizard.rules(), 2));
        assertEquals(TEST_RULE_SET_NAME, wizard.name().getText(), "The name of the ruleset should be loaded into the name text field");
        final String[] expectedNames = { "ExtendsObject", "PrimitiveWrapperInstantiation" };
        final String[] actualNames = wizard.ruleNames();
        assertArrayEquals(expectedNames, actualNames, "Rules of the PMD rule set");
        wizard.waitUntilFinishIsEnabled("The finish button should be enabled if both a name and a location are available");

        wizard.name().setText("");
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled if the name is not available");

        wizard.name().setText(WORKSPACE_RULE_SET_NAME);
        wizard.waitUntilFinishIsEnabled("The finish button should be enabled if the name is available");

        wizard.finish().click();
        wizard.bot().waitUntil(shellCloses(wizard));
        dialog.bot().waitUntil(tableHasRows(dialog.ruleSets(), 2));
        assertTrue(dialog.ruleSets().getTableItem(1).isChecked(), "The added rule set should be activated");
        assertEquals(WORKSPACE_RULE_SET_NAME, dialog.ruleSets().cell(1, "Name"), "Name of the rule set");
        assertEquals("Workspace", dialog.ruleSets().cell(1, "Type"), "Type of the rule set");
        assertEquals(workspaceRelativePath, dialog.ruleSets().cell(1, "Location"), "Location of the rule set");

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

    private void addProjectRuleSetInFirstProject() {
        final PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertTrue(dialog.enablePMD().isChecked(), "PMD should be enabled");
        assertTrue(dialog.addRuleSet().isEnabled(), "The button to add a new rule set should be enabled when PMD is enabled");

        dialog.enablePMD().select();
        dialog.addRuleSet().click();
        final AddRuleSetConfigurationWizardBot wizard = AddRuleSetConfigurationWizardBot.getActive();

        assertFalse(wizard.finish().isEnabled(), "The finish button should be disabled as long as the name is missing");

        wizard.project().click();
        wizard.next().click();
        assertFalse(wizard.finish().isEnabled(), "The finish button should be disabled as long as the name is missing");

        assertTrue(wizard.isBrowseButtonVisible(), "The browse button should be visible for a project rule set");

        final String projectRelativePath = PMD_XML.toString();
        wizard.location().setText(projectRelativePath);
        wizard.bot().waitUntil(tableHasRows(wizard.rules(), 2));
        assertEquals(TEST_RULE_SET_NAME, wizard.name().getText(), "The name of the ruleset should be loaded into the name text field");
        final String[] expectedNames = { "ExtendsObject", "PrimitiveWrapperInstantiation" };
        final String[] actualNames = wizard.ruleNames();
        assertArrayEquals(expectedNames, actualNames, "Rules of the PMD rule set");
        assertTrue(wizard.finish().isEnabled(),
                "The finish button should be enabled if both a name and a location with a valid rule set is available");

        wizard.name().setText("");
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled if the name is not available");

        wizard.name().setText(PROJECT_RULE_SET_NAME);
        wizard.waitUntilFinishIsEnabled("The finish button should be enabled if the name is available");

        wizard.finish().click();
        wizard.bot().waitUntil(shellCloses(wizard));
        dialog.bot().waitUntil(tableHasRows(dialog.ruleSets(), 3));
        assertTrue(dialog.ruleSets().getTableItem(2).isChecked(), "The added rule set should be activated");
        assertEquals(PROJECT_RULE_SET_NAME, dialog.ruleSets().cell(2, "Name"), "Name of the rule set");
        assertEquals("Project", dialog.ruleSets().cell(2, "Type"), "Type of the rule set");
        assertEquals(projectRelativePath, dialog.ruleSets().cell(2, "Location"), "Location of the rule set");

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

    private void addRemoteRuleSetInFirstProject() {
        final PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertTrue(dialog.enablePMD().isChecked(), "PMD should be enabled");
        assertTrue(dialog.addRuleSet().isEnabled(), "The button to add a new rule set should be enabled when PMD is enabled");

        dialog.addRuleSet().click();
        final AddRuleSetConfigurationWizardBot wizard = AddRuleSetConfigurationWizardBot.getActive();
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled as long as the name is missing");

        wizard.remote().click();
        wizard.next().click();
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled as long as the name is missing");

        assertFalse(wizard.isBrowseButtonVisible(), "The browse button should not be visible for a remote rule set");

        final String uri = rules.toUri().toString();
        wizard.location().setText(uri);
        wizard.bot().waitUntil(tableHasRows(wizard.rules(), 2));
        assertEquals(TEST_RULE_SET_NAME, wizard.name().getText(), "The name of the ruleset should be loaded into the name text field");
        final String[] expectedNames = { "ExtendsObject", "PrimitiveWrapperInstantiation" };
        final String[] actualNames = wizard.ruleNames();
        assertArrayEquals(expectedNames, actualNames, "Rules of the PMD rule set");
        wizard.waitUntilFinishIsEnabled("The finish button should be enabled if both a name and a location are available");

        wizard.name().setText("");
        wizard.waitUntilFinishIsDisabled("The finish button should be disabled if the name is not available");

        wizard.name().setText(REMOTE_RULE_SET_NAME);
        wizard.waitUntilFinishIsEnabled("The finish button should be enabled if the name is available");

        wizard.finish().click();
        wizard.bot().waitUntil(shellCloses(wizard));
        dialog.bot().waitUntil(tableHasRows(dialog.ruleSets(), 4));
        assertTrue(dialog.ruleSets().getTableItem(3).isChecked(), "The added rule set should be activated");
        assertEquals(REMOTE_RULE_SET_NAME, dialog.ruleSets().cell(3, "Name"), "Name of the rule set");
        assertEquals("Remote", dialog.ruleSets().cell(3, "Type"), "Type of the rule set");
        assertEquals(uri, dialog.ruleSets().cell(3, "Location"), "Location of the rule set");

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

    private void deactivateWorkspaceRuleSet() {
        PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertTrue(dialog.enablePMD().isChecked(), "PMD should be enabled");

        final SWTBotTableItem workspaceTableItem = dialog.ruleSets().getTableItem(WORKSPACE_RULE_SET_NAME);
        workspaceTableItem.uncheck();
        dialog.bot().waitWhile(isChecked(workspaceTableItem));

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));

        dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertEquals(3, dialog.ruleSets().rowCount(),
                "The deactivated rule set should not be in the table anymore since it is not used by any other project");

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

    private void deactivateFileSystemRuleSet() {
        PMDPropertyDialogBot dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertTrue(dialog.enablePMD().isChecked(), "PMD should be enabled");

        final SWTBotTableItem fileSystemTableItem = dialog.ruleSets().getTableItem(FILE_SYSTEM_RULE_SET_NAME);
        fileSystemTableItem.uncheck();
        dialog.bot().waitWhile(isChecked(fileSystemTableItem));

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));

        dialog = JavaProjectClient.openPMDPropertyDialog(PROJECT_NAME_1);
        assertFalse(dialog.ruleSets().getTableItem(FILE_SYSTEM_RULE_SET_NAME).isChecked(),
                "The deactivated rule set should still be in the table since it is used by project " + PROJECT_NAME_2);

        dialog.ok().click();
        dialog.bot().waitUntil(shellCloses(dialog));
    }

}
