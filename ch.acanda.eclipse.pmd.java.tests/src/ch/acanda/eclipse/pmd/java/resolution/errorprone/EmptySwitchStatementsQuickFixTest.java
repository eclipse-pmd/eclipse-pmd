package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptySwitchStatementsQuickFix;

/**
 * Unit plug-in test for {@link EmptySwitchStatementsQuickFix}.
 */
public class EmptySwitchStatementsQuickFixTest extends ASTQuickFixTestCase<EmptySwitchStatementsQuickFix> {

    public EmptySwitchStatementsQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptySwitchStatementsQuickFixTest.class.getResourceAsStream("EmptySwitchStatements.xml"));
    }

}
