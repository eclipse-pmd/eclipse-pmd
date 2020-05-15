package ch.acanda.eclipse.pmd.java.resolution.performance;

import static ch.acanda.eclipse.pmd.java.resolution.ASTUtil.copy;
import static ch.acanda.eclipse.pmd.java.resolution.ASTUtil.replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Position;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.Finders;
import ch.acanda.eclipse.pmd.java.resolution.NodeFinder;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Quick fix for the rule <a href=http://pmd.sourceforge.net/rules/java/migrating.html#LongInstantiation"
 * >LongInstantiation</a>. It replaces a Long instantiation with autoboxing.
 */
public class LongInstantiationAutoboxingQuickFix extends ASTQuickFix<ClassInstanceCreation> {

    private static final Pattern ARGUMENT = Pattern.compile("\\((.*)\\)");

    public LongInstantiationAutoboxingQuickFix(final PMDMarker marker) {
        super(marker);
    }

    @Override
    protected ImageDescriptor getImageDescriptor() {
        return PMDPluginImages.QUICKFIX_CHANGE;
    }

    @Override
    public String getLabel() {
        return "Use autoboxing";
    }

    @Override
    public String getDescription() {
        final Matcher matcher = ARGUMENT.matcher(marker.getMarkerText());
        if (matcher.find()) {
            return "Replaces the Long instantiation with <b>" + matcher.group(1) + "</b>.";
        }
        return "Uses autoboxing instead of an explicit Long instantiation";
    }

    @Override
    protected NodeFinder<CompilationUnit, ClassInstanceCreation> getNodeFinder(final Position position) {
        return Finders.positionWithinNode(position, getNodeType());
    }

    /**
     * Replaces the Long instantiation with its argument, e.g. {@code new Long(123 + x)} with {@code 123 + x}.
     */
    @Override
    protected boolean apply(final ClassInstanceCreation node) {
        final Expression argument = (Expression) node.arguments().get(0);
        replace(node, copy(argument));
        return true;
    }

}
