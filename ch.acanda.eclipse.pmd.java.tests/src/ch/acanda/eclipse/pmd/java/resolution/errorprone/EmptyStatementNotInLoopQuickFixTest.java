package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyStatementNotInLoopQuickFix;

/**
 * Unit plug-in test for {@link EmptyStatementNotInLoopQuickFix}.
 *
 * @author Philip Graf
 */
public class EmptyStatementNotInLoopQuickFixTest extends ASTQuickFixTestCase<EmptyStatementNotInLoopQuickFix> {

    public EmptyStatementNotInLoopQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptyStatementNotInLoopQuickFixTest.class.getResourceAsStream("EmptyStatementNotInLoop.xml"));
    }

}
