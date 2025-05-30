package ch.acanda.eclipse.pmd.marker;

import java.util.Locale;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

import net.sourceforge.pmd.lang.rule.Rule;
import net.sourceforge.pmd.reporting.RuleViolation;

/**
 * Utility for creating, adding and removing PMD markers.
 */
public final class MarkerUtil {

    private static final String MARKER_TYPE = "ch.acanda.eclipse.pmd.core.pmdMarker";
    private static final String LONG_MARKER_TYPE = "ch.acanda.eclipse.pmd.core.pmdLongMarker";

    private MarkerUtil() {
        // hide constructor of utility class
    }

    /**
     * Removes all PMD markers from a file.
     */
    public static void removeAllMarkers(final IFile file) throws CoreException {
        // This also deletes markers of type LONG_MARKER_TYPE as they are a subtype of MARKER_TYPE.
        file.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_ZERO);
    }

    /**
     * Removes all PMD markers from a project and all the files it contains.
     */
    public static void removeAllMarkers(final IProject project) throws CoreException {
        // This also deletes markers of type LONG_MARKER_TYPE as they are a subtype of MARKER_TYPE.
        project.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
    }

    /**
     * Adds a PMD Marker to a file.
     *
     * @param file The marker will be added to this file.
     * @param content The content of the file.
     * @param violation The PMD rule violation.
     * @return The created marker.
     * @throws CoreException Thrown when the file does not exist or its project is closed.
     */
    public static IMarker addMarker(final IFile file, final String content, final RuleViolation violation) throws CoreException {
        final boolean isLongMarker = violation.getBeginLine() != violation.getEndLine();
        final IMarker marker = file.createMarker(isLongMarker ? LONG_MARKER_TYPE : MARKER_TYPE);
        final WrappingPMDMarker pmdMarker = new WrappingPMDMarker(marker);
        marker.setAttribute(IMarker.MESSAGE, violation.getDescription());
        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
        marker.setAttribute(IMarker.LINE_NUMBER, Math.max(violation.getBeginLine(), 0));
        final Range range = getAbsoluteRange(content, violation);
        final int start = Math.max(range.start(), 0);
        marker.setAttribute(IMarker.CHAR_START, start);
        final int end = Math.max(range.end(), 0);
        marker.setAttribute(IMarker.CHAR_END, end);
        if (!isLongMarker) {
            pmdMarker.setMarkerText(content.substring(start, end));
        }
        final Map<String, String> info = violation.getAdditionalInfo();
        final Rule rule = violation.getRule();
        pmdMarker.setRuleId(createRuleId(rule));
        pmdMarker.setViolationClassName(info.get(RuleViolation.CLASS_NAME));
        pmdMarker.setVariableName(info.get(RuleViolation.VARIABLE_NAME));
        pmdMarker.setRuleName(rule.getName());
        pmdMarker.setLanguage(violation.getRule().getLanguage().getId());
        return marker;
    }

    public static String createRuleId(final Rule rule) {
        return rule.getLanguage().getId() + "." + rule.getRuleSetName().toLowerCase(Locale.ROOT) + "." + rule.getName();
    }

    public static Range getAbsoluteRange(final String content, final RuleViolation violation) {
        Range range;
        try {
            range = calculateAbsoluteRange(content, violation);
        } catch (final BadLocationException e) {
            range = new Range(0, 0);
        }
        return range;
    }

    private static Range calculateAbsoluteRange(final String content, final RuleViolation violation) throws BadLocationException {
        final Document document = new Document(content);

        // violation line and column start at one, the marker's start and end positions at zero
        final int start = getAbsolutePosition(content, document.getLineOffset(violation.getBeginLine() - 1), violation.getBeginColumn());
        final int end = getAbsolutePosition(content, document.getLineOffset(violation.getEndLine() - 1), violation.getEndColumn());

        // for some rules PMD creates violations with the end position before the start position
        final Range range;
        if (start <= end) {
            range = new Range(start - 1, end);
        } else {
            range = new Range(end - 1, start);
        }

        return range;
    }

    private static int getAbsolutePosition(final String content, final int lineOffset, final int pmdCharOffset) {
        int pmdCharCounter = 0;
        int absoluteOffset = lineOffset;
        while (pmdCharCounter < pmdCharOffset) {
            if (absoluteOffset < content.length()) {
                pmdCharCounter++;
            } else {
                break;
            }
            absoluteOffset++;
        }
        return absoluteOffset;
    }

    public record Range(int start, int end) {
    }

}
