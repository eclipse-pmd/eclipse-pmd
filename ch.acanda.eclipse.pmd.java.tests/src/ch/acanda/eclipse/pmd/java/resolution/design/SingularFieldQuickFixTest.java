package ch.acanda.eclipse.pmd.java.resolution.design;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link SingularFieldQuickFix}.
 */
public class SingularFieldQuickFixTest extends ASTQuickFixTestCase<SingularFieldQuickFix> {

    public SingularFieldQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(SingularFieldQuickFixTest.class.getResourceAsStream("SingularField.xml"));
    }

}
