package ch.acanda.eclipse.pmd.java.resolution.codestyle;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link UnnecessaryReturnQuickFix}.
 *
 * @author Philip Graf
 */
public class UnnecessaryReturnQuickFixTest extends ASTQuickFixTestCase<UnnecessaryReturnQuickFix> {

    public UnnecessaryReturnQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(UnnecessaryReturnQuickFixTest.class.getResourceAsStream("UnnecessaryReturn.xml"));
    }

}
