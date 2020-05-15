package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link ShortInstantiationValueOfQuickFix}.
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class ShortInstantiationValueOfQuickFixTest extends ASTQuickFixTestCase<ShortInstantiationValueOfQuickFix> {

    public ShortInstantiationValueOfQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(ShortInstantiationValueOfQuickFixTest.class.getResourceAsStream("ShortInstantiationValueOf.xml"));
    }

}
