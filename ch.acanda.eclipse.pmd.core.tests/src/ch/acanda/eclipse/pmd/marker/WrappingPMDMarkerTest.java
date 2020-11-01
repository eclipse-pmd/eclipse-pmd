package ch.acanda.eclipse.pmd.marker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link WrappingPMDMarker}.
 */
public class WrappingPMDMarkerTest {

    private static final String RULE_ID = "ruleId";

    /**
     * Verifies that {@link WrappingPMDMarker#isOtherWithSameRuleId(IMarker)} returns true if the argument is not the
     * same instance but has the same rule id.
     */
    @Test
    public void isOtherWithSameRuleId() {
        final IMarker marker = mock(IMarker.class);
        final String ruleId = "Rule A";
        when(marker.getAttribute(eq(RULE_ID), isA(String.class))).thenReturn(ruleId);
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final IMarker other = mock(IMarker.class);
        when(other.getAttribute(eq(RULE_ID), isA(String.class))).thenReturn(ruleId);
        final boolean actual = pmdMarker.isOtherWithSameRuleId(other);
        assertTrue(actual, "The marker must not be the same instance as the other marker");
    }

    /**
     * Verifies that {@link WrappingPMDMarker#isOtherWithSameRuleId(IMarker)} returns false if the argument is the same
     * instance.
     */
    @Test
    public void isOtherWithSameRuleIdSameInstance() {
        final IMarker marker = mock(IMarker.class);
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final IMarker other = marker;
        final boolean actual = pmdMarker.isOtherWithSameRuleId(other);
        assertFalse(actual, "The marker must not be the same instance as the other marker");
    }

    /**
     * Verifies that {@link WrappingPMDMarker#isOtherWithSameRuleId(IMarker)} returns false if the argument has not the
     * same rule id.
     */
    @Test
    public void isOtherWithSameRuleIdDifferentRuleId() {
        final IMarker marker = mock(IMarker.class);
        when(marker.getAttribute(eq(RULE_ID), isA(String.class))).thenReturn("Rule B");
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final IMarker other = mock(IMarker.class);
        when(other.getAttribute(eq(RULE_ID), isA(String.class))).thenReturn("Rule C");
        final boolean actual = pmdMarker.isOtherWithSameRuleId(other);
        assertFalse(actual, "The marker must not be the same instance as the other marker");
    }

    /**
     * Verifies that {@link WrappingPMDMarker#setRuleId(String)} sets the rule id on the wrapped marker.
     */
    @Test
    public void setRuleId() throws CoreException {
        final IMarker marker = mock(IMarker.class);
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final String expected = "Rule D";
        pmdMarker.setRuleId(expected);
        verify(marker).setAttribute(RULE_ID, expected);
    }

    /**
     * Verifies that {@link WrappingPMDMarker#getRuleId()} gets the rule id from the wrapped marker.
     */
    @Test
    public void shouldReturnExpectedRuleId() throws CoreException {
        final IMarker marker = mock(IMarker.class);
        final String expected = "Rule E";
        when(marker.getAttribute(eq(RULE_ID), isNull())).thenReturn(expected);
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final String actual = pmdMarker.getRuleId();
        assertEquals(expected, actual, "The rule id should be read from the wrapped marker");
    }

    /**
     * Verifies that {@link WrappingPMDMarker#setViolationClassName(String)} sets the violation class name on the
     * wrapped marker.
     */
    @Test
    public void setViolationClassName() throws CoreException {
        final IMarker marker = mock(IMarker.class);
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final String expected = "ViolationClassName";
        pmdMarker.setViolationClassName(expected);
        verify(marker).setAttribute("violationClassName", expected);
    }

    /**
     * Verifies that {@link WrappingPMDMarker#getViolationClassName()} gets the violation class name from the wrapped
     * marker.
     */
    @Test
    public void shouldReturnExpectedViolationClassName() throws CoreException {
        final IMarker marker = mock(IMarker.class);
        final String expected = "ViolationClassName";
        when(marker.getAttribute(eq("violationClassName"), anyString())).thenReturn(expected);
        final PMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final String actual = pmdMarker.getViolationClassName();
        assertEquals(expected, actual, "The rule id should be read from the wrapped marker");
    }

    /**
     * Verifies that {@link WrappingPMDMarker#setLanguage(String)} sets the language on the wrapped marker.
     */
    @Test
    public void setLanguage() throws CoreException {
        final IMarker marker = mock(IMarker.class);
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        final String expected = "java";
        pmdMarker.setLanguage(expected);
        verify(marker).setAttribute("language", expected);
    }

}
