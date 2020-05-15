package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyStatementBlockQuickFix;

/**
 * Unit plug-in test for {@link EmptyStatementBlockQuickFix}.
 */
public class EmptyStatementBlockQuickFixTest extends ASTQuickFixTestCase<EmptyStatementBlockQuickFix> {

    public EmptyStatementBlockQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptyStatementBlockQuickFixTest.class.getResourceAsStream("EmptyStatementBlock.xml"));
    }

}
