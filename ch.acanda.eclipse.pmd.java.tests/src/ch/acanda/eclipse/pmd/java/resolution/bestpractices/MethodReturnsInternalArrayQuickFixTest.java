package ch.acanda.eclipse.pmd.java.resolution.bestpractices;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link MethodReturnsInternalArray}.
 *
 * @author Philip Graf
 */
public class MethodReturnsInternalArrayQuickFixTest extends ASTQuickFixTestCase<MethodReturnsInternalArrayQuickFix> {

    public MethodReturnsInternalArrayQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(MethodReturnsInternalArrayQuickFix.class.getResourceAsStream("MethodReturnsInternalArray.xml"));
    }

}
