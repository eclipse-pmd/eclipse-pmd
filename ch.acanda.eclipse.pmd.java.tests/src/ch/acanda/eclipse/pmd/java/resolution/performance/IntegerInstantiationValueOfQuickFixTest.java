package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link IntegerInstantiationValueOfQuickFix}.
 *
 * @author Philip Graf
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class IntegerInstantiationValueOfQuickFixTest extends ASTQuickFixTestCase<IntegerInstantiationValueOfQuickFix> {

    public IntegerInstantiationValueOfQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(IntegerInstantiationValueOfQuickFixTest.class.getResourceAsStream("IntegerInstantiationValueOf.xml"));
    }

}
