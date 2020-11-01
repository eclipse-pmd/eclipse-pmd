package ch.acanda.eclipse.pmd.java.resolution;

import java.util.Collection;

/**
 * Unit plug-in test for {@link SuppressWarningsQuickFix}.
 */
public class SuppressWarningsQuickFixTest extends ASTQuickFixTestCase<SuppressWarningsQuickFix> {

    public static Collection<Object[]> getTestData() {
        return createTestData(SuppressWarningsQuickFixTest.class.getResourceAsStream("SuppressWarnings.xml"));
    }

}
