package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link SuspiciousHashcodeMethodNameQuickFix}.
 *
 * @author Philip Graf
 */
public class SuspiciousHashcodeMethodNameQuickFixTest extends ASTQuickFixTestCase<SuspiciousHashcodeMethodNameQuickFix> {

    public SuspiciousHashcodeMethodNameQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(SuspiciousHashcodeMethodNameQuickFixTest.class.getResourceAsStream("SuspiciousHashcodeMethodName.xml"));
    }

}
