package ch.acanda.eclipse.pmd.java.resolution.codestyle;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFixTestCase;
import ch.acanda.eclipse.pmd.java.resolution.QuickFixTestData.TestParameters;

/**
 * Unit plug-in test for {@link ExtendsObjectQuickFix}.
 */
public class ExtendsObjectQuickFixTest extends ASTQuickFixTestCase<ExtendsObjectQuickFix> {

    public ExtendsObjectQuickFixTest(final TestParameters parameters) {
        super(parameters);
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return createTestData(ExtendsObjectQuickFixTest.class.getResourceAsStream("ExtendsObject.xml"));
    }

}
