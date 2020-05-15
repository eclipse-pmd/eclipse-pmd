package ch.acanda.eclipse.pmd.java.resolution.design;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.TextEditQuickFixTestCase;

/**
 * Unit plug-in test for {@link UseUtilityClassQuickFix}.
 *
 * @author Philip Graf
 */
public class UseUtilityClassQuickFixTest extends TextEditQuickFixTestCase<UseUtilityClassQuickFix> {

    public UseUtilityClassQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(UseUtilityClassQuickFixTest.class.getResourceAsStream("UseUtilityClass.xml"));
    }

}
