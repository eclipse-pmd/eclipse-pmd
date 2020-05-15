package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link UnnecessaryCaseChangeQuickFix}.
 */
public class UnnecessaryCaseChangeQuickFixTest extends ASTQuickFixTestCase<UnnecessaryCaseChangeQuickFix> {

    public UnnecessaryCaseChangeQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(UnnecessaryCaseChangeQuickFixTest.class.getResourceAsStream("UnnecessaryCaseChange.xml"));
    }

}
