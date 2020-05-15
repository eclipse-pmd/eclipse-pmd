package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptySynchronizedBlockQuickFix;

/**
 * Unit plug-in test for {@link EmptySynchronizedBlockQuickFix}.
 */
public class EmptySynchronizedBlockQuickFixTest extends ASTQuickFixTestCase<EmptySynchronizedBlockQuickFix> {

    public EmptySynchronizedBlockQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptySynchronizedBlockQuickFixTest.class.getResourceAsStream("EmptySynchronizedBlock.xml"));
    }

}
