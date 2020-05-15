package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link LongInstantiationAutoboxingQuickFix}.
 *
 * @author Philip Graf
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class LongInstantiationAutoboxingQuickFixTest extends ASTQuickFixTestCase<LongInstantiationAutoboxingQuickFix> {

    public LongInstantiationAutoboxingQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(LongInstantiationAutoboxingQuickFixTest.class.getResourceAsStream("LongInstantiationAutoboxing.xml"));
    }

}
