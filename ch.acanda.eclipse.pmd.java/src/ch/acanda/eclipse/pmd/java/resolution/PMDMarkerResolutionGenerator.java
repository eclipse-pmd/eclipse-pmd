package ch.acanda.eclipse.pmd.java.resolution;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.osgi.framework.Version;

import ch.acanda.eclipse.pmd.marker.WrappingPMDMarker;

/**
 * Creates resolutions for a Java PMD marker.
 */
public class PMDMarkerResolutionGenerator implements IMarkerResolutionGenerator2 {

    private final JavaQuickFixGenerator quickFixGenerator = new JavaQuickFixGenerator();

    @Override
    public boolean hasResolutions(final IMarker marker) {
        final JavaQuickFixContext context = new JavaQuickFixContext(getCompilerCompliance(marker));
        return quickFixGenerator.hasQuickFixes(new WrappingPMDMarker(marker), context);
    }

    @Override
    public IMarkerResolution[] getResolutions(final IMarker marker) {
        final JavaQuickFixContext context = new JavaQuickFixContext(getCompilerCompliance(marker));
        final List<? extends IMarkerResolution> quickFixes = quickFixGenerator.getQuickFixes(new WrappingPMDMarker(marker), context);
        return quickFixes.toArray(new IMarkerResolution[0]);
    }

    private Version getCompilerCompliance(final IMarker marker) {
        final IJavaProject project = JavaCore.create(marker.getResource().getProject());
        final String compilerCompliance = project.getOption(JavaCore.COMPILER_COMPLIANCE, true);
        return new Version(compilerCompliance);
    }

}
