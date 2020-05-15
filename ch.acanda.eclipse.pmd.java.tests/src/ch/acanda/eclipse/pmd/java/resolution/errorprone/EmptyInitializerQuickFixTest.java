package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyInitializerQuickFix;

/**
 * Unit plug-in test for {@link EmptyInitializerQuickFix}.
 *
 * @author Philip Graf
 */
public class EmptyInitializerQuickFixTest extends ASTQuickFixTestCase<EmptyInitializerQuickFix> {

    public EmptyInitializerQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptyInitializerQuickFixTest.class.getResourceAsStream("EmptyInitializer.xml"));
    }

}
