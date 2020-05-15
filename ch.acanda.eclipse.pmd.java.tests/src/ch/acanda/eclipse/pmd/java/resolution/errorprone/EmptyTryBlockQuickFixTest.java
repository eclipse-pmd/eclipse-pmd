package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyTryBlockQuickFix;

/**
 * Unit plug-in test for {@link EmptyTryBlockQuickFix}.
 *
 * @author Philip Graf
 */
public class EmptyTryBlockQuickFixTest extends ASTQuickFixTestCase<EmptyTryBlockQuickFix> {

    public EmptyTryBlockQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptyTryBlockQuickFixTest.class.getResourceAsStream("EmptyTryBlock.xml"));
    }

}
