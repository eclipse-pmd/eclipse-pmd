package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link EqualsNullQuickFix}.
 *
 * @author Philip Graf
 */
public class EqualsNullQuickFixTest extends ASTQuickFixTestCase<EqualsNullQuickFix> {

    public EqualsNullQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EqualsNullQuickFixTest.class.getResourceAsStream("EqualsNull.xml"));
    }

}
