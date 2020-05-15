package ch.acanda.eclipse.pmd.builder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import ch.acanda.eclipse.pmd.marker.MarkerUtil;
import net.sourceforge.pmd.RuleViolation;

/**
 * Processes the rule violations found by a PMD analysis.
 *
 * @author Philip Graf
 */
public class ViolationProcessor {

    public void annotate(final IFile file, final Iterable<RuleViolation> violations) throws CoreException, IOException {
        MarkerUtil.removeAllMarkers(file);
        if (violations.iterator().hasNext()) {
            final String content = Files.readString(file.getRawLocation().toFile().toPath(), Charset.forName(file.getCharset()));
            for (final RuleViolation violation : violations) {
                MarkerUtil.addMarker(file, content, violation);
            }
        }
    }

}
