package ch.acanda.eclipse.pmd.java.resolution.codestyle;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link LocalVariableCouldBeFinalQuickFix}.
 *
 * @author Philip Graf
 */
public class LocalVariableCouldBeFinalQuickFixTest extends ASTQuickFixTestCase<LocalVariableCouldBeFinalQuickFix> {

    public LocalVariableCouldBeFinalQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(LocalVariableCouldBeFinalQuickFixTest.class.getResourceAsStream("LocalVariableCouldBeFinal.xml"));
    }

}
