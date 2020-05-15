package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyWhileStmtQuickFix;

/**
 * Unit plug-in test for {@link EmptyWhileStmtQuickFix}.
 */
public class EmptyWhileStmtQuickFixTest extends ASTQuickFixTestCase<EmptyWhileStmtQuickFix> {

    public EmptyWhileStmtQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptyWhileStmtQuickFixTest.class.getResourceAsStream("EmptyWhileStmt.xml"));
    }

}
