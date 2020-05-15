package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyStaticInitializerQuickFix;

/**
 * Unit plug-in test for {@link EmptyStaticInitializerQuickFix}.
 *
 * @author Philip Graf
 */
public class EmptyStaticInitializerQuickFixTest extends ASTQuickFixTestCase<EmptyStaticInitializerQuickFix> {

    public EmptyStaticInitializerQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(EmptyStaticInitializerQuickFixTest.class.getResourceAsStream("EmptyStaticInitializer.xml"));
    }

}
