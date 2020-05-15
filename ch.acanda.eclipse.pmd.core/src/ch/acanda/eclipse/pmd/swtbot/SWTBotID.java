package ch.acanda.eclipse.pmd.swtbot;

import org.eclipse.swt.widgets.Control;

/**
 * @author Philip Graf
 */
public enum SWTBotID {

    ENABLE_PMD, ADD, NAME, LOCATION, RULES, RULESETS, FILE_SYSTEM, WORKSPACE, PROJECT, REMOTE, BROWSE, FILE_SELECTION_DIALOG, PMD_VERSION;

    public static void set(final Control control, final SWTBotID id) {
        control.setData("org.eclipse.swtbot.widget.key", id.name());
    }

}
