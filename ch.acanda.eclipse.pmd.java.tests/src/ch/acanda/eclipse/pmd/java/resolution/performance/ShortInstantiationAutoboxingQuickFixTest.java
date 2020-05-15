package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link ShortInstantiationAutoboxingQuickFix}.
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class ShortInstantiationAutoboxingQuickFixTest extends ASTQuickFixTestCase<ShortInstantiationAutoboxingQuickFix> {

    public ShortInstantiationAutoboxingQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(ShortInstantiationAutoboxingQuickFixTest.class.getResourceAsStream("ShortInstantiationAutoboxing.xml"));
    }

}
