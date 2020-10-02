package ch.acanda.eclipse.pmd.swtbot.condition;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;

/**
 * This is an factory class to create conditions which can be used for
 * {@link org.eclipse.swtbot.swt.finder.SWTBot#waitUntil(org.eclipse.swtbot.swt.finder.waits.ICondition)
 * SWTBot.waitUntil(...)} and
 * {@link org.eclipse.swtbot.swt.finder.SWTBot#waitWhile(org.eclipse.swtbot.swt.finder.waits.ICondition)
 * SWTBot.waitWhile(...)}.
 */
public final class Conditions {

    private Conditions() {
        // hide constructor of utility class
    }

    /**
     * Returns a condition that tests if a table item is checked.
     *
     * @see IsChecked
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public static ICondition isChecked(final SWTBotTableItem tableItem) {
        return new IsChecked(tableItem);
    }

    /**
     * Returns a condition that tests if a perspective is active.
     *
     * @see IsPerspectiveActive
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public static ICondition isPerspectiveActive(final SWTBotPerspective perspective) {
        return new IsPerspectiveActive(perspective);
    }

}
