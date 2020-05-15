package ch.acanda.eclipse.pmd.java.resolution.emptycode;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Position;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.Finders;
import ch.acanda.eclipse.pmd.java.resolution.NodeFinder;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Quick fix for the rule
 * <a href="http://pmd.sourceforge.net/rules/java/empty.html#EmptyStatementNotInLoop" >EmptyStatementNotInLoop</a>. It
 * removes the empty statement.
 *
 * @author Philip Graf
 */
public class EmptyStatementNotInLoopQuickFix extends ASTQuickFix<EmptyStatement> {

    public EmptyStatementNotInLoopQuickFix(final PMDMarker marker) {
        super(marker);
    }

    @Override
    protected ImageDescriptor getImageDescriptor() {
        return PMDPluginImages.QUICKFIX_REMOVE;
    }

    @Override
    public String getLabel() {
        return "Remove the empty statement";
    }

    @Override
    public String getDescription() {
        return "Removes the empty statement.";
    }

    @Override
    protected NodeFinder<CompilationUnit, EmptyStatement> getNodeFinder(final Position position) {
        return Finders.positionWithinNode(position, getNodeType());
    }

    @Override
    protected boolean apply(final EmptyStatement node) {
        node.delete();
        return true;
    }

}
