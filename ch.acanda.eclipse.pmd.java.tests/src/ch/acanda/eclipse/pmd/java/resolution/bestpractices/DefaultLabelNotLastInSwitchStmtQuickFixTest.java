package ch.acanda.eclipse.pmd.java.resolution.bestpractices;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link DefaultLabelNotLastInSwitchStmtQuickFix}.
 */
public class DefaultLabelNotLastInSwitchStmtQuickFixTest extends ASTQuickFixTestCase<DefaultLabelNotLastInSwitchStmtQuickFix> {

    public DefaultLabelNotLastInSwitchStmtQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(DefaultLabelNotLastInSwitchStmtQuickFixTest.class.getResourceAsStream("DefaultLabelNotLastInSwitchStmt.xml"));
    }

}
