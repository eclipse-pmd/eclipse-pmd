package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link SimplifyStartsWithQuickFix}.
 *
 * @author Philip Graf
 */
public class SimplifyStartsWithQuickFixTest extends ASTQuickFixTestCase<SimplifyStartsWithQuickFix> {

    public SimplifyStartsWithQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(SimplifyStartsWithQuickFixTest.class.getResourceAsStream("SimplifyStartsWith.xml"));
    }

}
