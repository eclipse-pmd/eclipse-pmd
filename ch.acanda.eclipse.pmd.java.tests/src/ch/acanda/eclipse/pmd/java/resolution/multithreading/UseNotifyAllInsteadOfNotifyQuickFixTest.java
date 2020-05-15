package ch.acanda.eclipse.pmd.java.resolution.multithreading;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link UseNotifyAllInsteadOfNotifyQuickFix}.
 *
 * @author Philip Graf
 */
public class UseNotifyAllInsteadOfNotifyQuickFixTest extends ASTQuickFixTestCase<UseNotifyAllInsteadOfNotifyQuickFix> {

    public UseNotifyAllInsteadOfNotifyQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(UseNotifyAllInsteadOfNotifyQuickFixTest.class.getResourceAsStream("UseNotifyAllInsteadOfNotify.xml"));
    }

}
