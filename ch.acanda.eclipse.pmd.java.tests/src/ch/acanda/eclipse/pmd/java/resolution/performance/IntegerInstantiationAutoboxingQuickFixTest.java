package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link IntegerInstantiationAutoboxingQuickFix}.
 *
 * @author Philip Graf
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class IntegerInstantiationAutoboxingQuickFixTest extends ASTQuickFixTestCase<IntegerInstantiationAutoboxingQuickFix> {

    public IntegerInstantiationAutoboxingQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(IntegerInstantiationAutoboxingQuickFixTest.class.getResourceAsStream("IntegerInstantiationAutoboxing.xml"));
    }

}
