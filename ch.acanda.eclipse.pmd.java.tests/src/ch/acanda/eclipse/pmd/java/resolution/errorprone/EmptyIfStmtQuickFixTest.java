package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyIfStmtQuickFix;

/**
 * Unit plug-in test for {@link EmptyIfStmtQuickFix}.
 */
public class EmptyIfStmtQuickFixTest extends ASTQuickFixTestCase<EmptyIfStmtQuickFix> {

    public EmptyIfStmtQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptyIfStmtQuickFixTest.class.getResourceAsStream("EmptyIfStmt.xml"));
    }

}
