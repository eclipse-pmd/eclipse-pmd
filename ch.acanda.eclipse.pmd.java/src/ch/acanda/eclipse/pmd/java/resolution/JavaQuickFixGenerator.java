package ch.acanda.eclipse.pmd.java.resolution;

import java.util.List;

import org.eclipse.ui.IMarkerResolution;
import org.osgi.framework.Version;

import ch.acanda.eclipse.pmd.marker.PMDMarker;

public final class JavaQuickFixGenerator {

    private static final Version JAVA_5 = new Version(1, 5, 0);

    public boolean hasQuickFixes(final PMDMarker marker, final JavaQuickFixContext context) {
        return context.compilerCompliance().compareTo(JAVA_5) >= 0;
    }

    public List<? extends IMarkerResolution> getQuickFixes(final PMDMarker marker, final JavaQuickFixContext context) {
        if (context.compilerCompliance().compareTo(JAVA_5) < 0) {
            return List.of();
        }
        return List.of(new SuppressWarningsQuickFix(marker));
    }

}
