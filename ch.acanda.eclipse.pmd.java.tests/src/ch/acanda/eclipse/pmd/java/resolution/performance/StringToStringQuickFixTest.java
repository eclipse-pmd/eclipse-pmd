package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link StringToStringQuickFix}.
 *
 * @author Philip Graf
 */
public class StringToStringQuickFixTest extends ASTQuickFixTestCase<StringToStringQuickFix> {

    public StringToStringQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(StringToStringQuickFixTest.class.getResourceAsStream("StringToString.xml"));
    }

}
