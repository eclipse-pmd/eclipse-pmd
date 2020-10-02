package ch.acanda.eclipse.pmd.java.resolution;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.ui.IMarkerResolution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.osgi.framework.Version;

import ch.acanda.eclipse.pmd.marker.PMDMarker;

@RunWith(Parameterized.class)
public class JavaQuickFixGeneratorTest {

    private final String ruleId;
    private final String javaVersion;
    private final Class<? extends IMarkerResolution>[] expectedQuickFixClasses;

    private PMDMarker marker;
    private JavaQuickFixContext context;

    @SafeVarargs
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public JavaQuickFixGeneratorTest(final String ruleId,
            final String javaVersion,
            final Class<? extends IMarkerResolution>... expectedQuickFixClasses) {
        this.ruleId = ruleId;
        this.javaVersion = javaVersion;
        this.expectedQuickFixClasses = expectedQuickFixClasses;
    }

    @Parameters
    public static Collection<Object[]> getTestData() {
        return Collections.singletonList(createTestData("java.code style.ExtendsObject", "11", SuppressWarningsQuickFix.class));
    }

    @SafeVarargs
    private static Object[] createTestData(final String ruleId, final String javaVersion,
            final Class<? extends IMarkerResolution>... classes) {
        return new Object[] { ruleId, javaVersion, classes };
    }

    @Before
    public void setUp() {
        marker = mock(PMDMarker.class);
        when(marker.getRuleId()).thenReturn(ruleId);
        context = new JavaQuickFixContext(new Version(javaVersion));
    }

    @Test
    public void hasQuickFixes() {
        final boolean hasQuickFixes = new JavaQuickFixGenerator().hasQuickFixes(marker, context);

        assertEquals("hasQuickFixes should return whether the generator has quick fixes for the rule " + ruleId
                + " and java version: " + javaVersion, expectedQuickFixClasses.length > 0, hasQuickFixes);
    }

    @Test
    public void testGetQuickFixes() {
        final Class<?>[] actualQuickFixClasses =
                new JavaQuickFixGenerator().getQuickFixes(marker, context).stream().map(IMarkerResolution::getClass).toArray(Class[]::new);

        assertArrayEquals("Quick fixes for rule " + ruleId + " and java version " + javaVersion, expectedQuickFixClasses,
                actualQuickFixClasses);
    }

}
