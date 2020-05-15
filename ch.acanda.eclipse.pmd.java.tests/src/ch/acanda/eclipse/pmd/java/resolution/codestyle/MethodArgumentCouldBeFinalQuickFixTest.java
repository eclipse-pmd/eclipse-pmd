package ch.acanda.eclipse.pmd.java.resolution.codestyle;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link MethodArgumentCouldBeFinalQuickFix}.
 */
public class MethodArgumentCouldBeFinalQuickFixTest extends ASTQuickFixTestCase<MethodArgumentCouldBeFinalQuickFix> {

    public MethodArgumentCouldBeFinalQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(MethodArgumentCouldBeFinalQuickFixTest.class.getResourceAsStream("MethodArgumentCouldBeFinal.xml"));
    }

}
