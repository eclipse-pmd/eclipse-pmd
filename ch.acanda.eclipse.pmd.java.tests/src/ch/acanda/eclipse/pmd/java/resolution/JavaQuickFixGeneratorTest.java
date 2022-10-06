package ch.acanda.eclipse.pmd.java.resolution;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.ui.IMarkerResolution;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Version;

import ch.acanda.eclipse.pmd.marker.PMDMarker;

public class JavaQuickFixGeneratorTest {

    @Test
    public void hasQuickFixes() {
        final String ruleId = "java.code style.ExtendsObject";
        final String javaVersion = "17";
        final PMDMarker marker = mock(PMDMarker.class);
        when(marker.getRuleId()).thenReturn(ruleId);
        final JavaQuickFixContext context = new JavaQuickFixContext(new Version(javaVersion));

        final boolean hasQuickFixes = new JavaQuickFixGenerator().hasQuickFixes(marker, context);

        assertTrue(hasQuickFixes,
                () -> "hasQuickFixes should return whether the generator has quick fixes for the rule " + ruleId + " and java version "
                        + javaVersion);
    }

    @Test
    public void testGetQuickFixes() {
        final String ruleId = "java.code style.ExtendsObject";
        final String javaVersion = "17";
        final Class<?>[] expectedQuickFixClasses = { SuppressWarningsQuickFix.class };
        final PMDMarker marker = mock(PMDMarker.class);
        when(marker.getRuleId()).thenReturn(ruleId);
        final JavaQuickFixContext context = new JavaQuickFixContext(new Version(javaVersion));

        final Class<?>[] actualQuickFixClasses =
                new JavaQuickFixGenerator().getQuickFixes(marker, context).stream().map(IMarkerResolution::getClass).toArray(Class[]::new);

        assertArrayEquals(expectedQuickFixClasses, actualQuickFixClasses,
                () -> "Quick fixes for rule " + ruleId + " and java version " + javaVersion);
    }

}
