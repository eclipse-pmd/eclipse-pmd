package ch.acanda.eclipse.pmd.java.resolution.design;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link UselessOverridingMethodQuickFix}.
 */
public class UselessOverridingMethodQuickFixTest extends ASTQuickFixTestCase<UselessOverridingMethodQuickFix> {

    public UselessOverridingMethodQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(UselessOverridingMethodQuickFixTest.class.getResourceAsStream("UselessOverridingMethod.xml"));
    }

}
