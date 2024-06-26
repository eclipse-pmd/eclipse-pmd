package ch.acanda.eclipse.pmd.builder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import ch.acanda.eclipse.pmd.marker.MarkerUtil;
import net.sourceforge.pmd.reporting.RuleViolation;

/**
 * Processes the rule violations found by a PMD analysis.
 */
public class ViolationProcessor {

    public void annotate(final IFile file, final List<RuleViolation> violations) throws CoreException, IOException {
        MarkerUtil.removeAllMarkers(file);
        if (!violations.isEmpty()) {
            final String content = Files.readString(file.getRawLocation().toFile().toPath(), Charset.forName(file.getCharset()));
            for (final RuleViolation violation : violations) {
                MarkerUtil.addMarker(file, content, violation);
            }
        }
    }

}
