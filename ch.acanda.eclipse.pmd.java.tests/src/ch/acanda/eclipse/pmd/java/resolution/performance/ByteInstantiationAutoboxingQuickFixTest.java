package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link ByteInstantiationAutoboxingQuickFix}.
 */
@SuppressWarnings("PMD.UseUtilityClass")
public class ByteInstantiationAutoboxingQuickFixTest extends ASTQuickFixTestCase<ByteInstantiationAutoboxingQuickFix> {

    public ByteInstantiationAutoboxingQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(ByteInstantiationAutoboxingQuickFixTest.class.getResourceAsStream("ByteInstantiationAutoboxing.xml"));
    }

}
