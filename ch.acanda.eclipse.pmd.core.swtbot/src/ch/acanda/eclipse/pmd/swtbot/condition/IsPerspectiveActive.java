package ch.acanda.eclipse.pmd.swtbot.condition;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

/**
 * This condition tests if a perspective is checked.
 */
public final class IsPerspectiveActive extends DefaultCondition {

    private final SWTBotPerspective perspective;

    public IsPerspectiveActive(final SWTBotPerspective perspective) {
        this.perspective = perspective;
    }

    @Override
    public boolean test() throws Exception {
        return perspective.isActive();
    }

    @Override
    public String getFailureMessage() {
        return "Perspective " + perspective.getLabel() + " is not active";
    }

}
