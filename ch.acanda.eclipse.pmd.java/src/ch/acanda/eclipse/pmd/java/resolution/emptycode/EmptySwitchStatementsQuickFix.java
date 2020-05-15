package ch.acanda.eclipse.pmd.java.resolution.emptycode;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Position;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.Finders;
import ch.acanda.eclipse.pmd.java.resolution.NodeFinder;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Quick fix for the rule
 * <a href="http://pmd.sourceforge.net/rules/java/empty.html#EmptySwitchStatements" >EmptySwitchStatements</a>. It
 * removes the empty switch statement.
 */
public class EmptySwitchStatementsQuickFix extends ASTQuickFix<SwitchStatement> {

    public EmptySwitchStatementsQuickFix(final PMDMarker marker) {
        super(marker);
    }

    @Override
    protected ImageDescriptor getImageDescriptor() {
        return PMDPluginImages.QUICKFIX_REMOVE;
    }

    @Override
    public String getLabel() {
        return "Remove the switch statement";
    }

    @Override
    public String getDescription() {
        return "Removes the empty switch statement.";
    }

    @Override
    protected NodeFinder<CompilationUnit, SwitchStatement> getNodeFinder(final Position position) {
        return Finders.positionWithinNode(position, getNodeType());
    }

    @Override
    protected boolean apply(final SwitchStatement node) {
        node.delete();
        return true;
    }

}
