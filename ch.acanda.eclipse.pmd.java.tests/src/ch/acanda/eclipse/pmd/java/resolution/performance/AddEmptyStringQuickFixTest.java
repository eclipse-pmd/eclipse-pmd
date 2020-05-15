package ch.acanda.eclipse.pmd.java.resolution.performance;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link AddEmptyStringQuickFix}.
 */
public class AddEmptyStringQuickFixTest extends ASTQuickFixTestCase<AddEmptyStringQuickFix> {

    public AddEmptyStringQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(AddEmptyStringQuickFixTest.class.getResourceAsStream("AddEmptyString.xml"));
    }

}
