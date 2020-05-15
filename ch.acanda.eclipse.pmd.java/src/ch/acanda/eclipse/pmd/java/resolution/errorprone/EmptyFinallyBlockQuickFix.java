package ch.acanda.eclipse.pmd.java.resolution.errorprone;

import static ch.acanda.eclipse.pmd.java.resolution.ASTUtil.copy;
import static ch.acanda.eclipse.pmd.java.resolution.ASTUtil.replace;

import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Position;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.Finders;
import ch.acanda.eclipse.pmd.java.resolution.NodeFinder;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Quick fix for the rule
 * <a href="http://pmd.sourceforge.net/rules/java/empty.html#EmptyFinallyBlock" >EmptyFinallyBlock</a>. It removes the
 * empty finally block.
 *
 * @author Philip Graf
 */
public class EmptyFinallyBlockQuickFix extends ASTQuickFix<TryStatement> {

    public EmptyFinallyBlockQuickFix(final PMDMarker marker) {
        super(marker);
    }

    @Override
    protected ImageDescriptor getImageDescriptor() {
        return PMDPluginImages.QUICKFIX_REMOVE;
    }

    @Override
    public String getLabel() {
        return "Remove the finally block";
    }

    @Override
    public String getDescription() {
        return "Removes the empty finally block.";
    }

    @Override
    protected NodeFinder<CompilationUnit, TryStatement> getNodeFinder(final Position position) {
        return Finders.positionWithinNode(position, getNodeType());
    }

    /**
     * Removes the finally block. Additionally removes the try if there are no catch blocks while keeping the try block
     * statements.
     */
    @Override
    protected boolean apply(final TryStatement node) {
        boolean success = true;
        if (node.catchClauses().isEmpty()) {
            @SuppressWarnings("unchecked")
            final List<Statement> statements = node.getBody().statements();
            if (replace(node, copy(statements))) {
                node.delete();
            } else {
                success = false;
            }
        } else {
            node.setFinally(null);
        }
        return success;
    }

}
