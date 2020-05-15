package ch.acanda.eclipse.pmd.java.resolution.codestyle;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Position;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.Finders;
import ch.acanda.eclipse.pmd.java.resolution.NodeFinder;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Quick fix for the rule
 * <a href="http://pmd.sourceforge.net/rules/java/unnecessary.html#UnnecessaryReturn">UnnecessaryReturn</a>. It removes
 * the return statement.
 */
public class UnnecessaryReturnQuickFix extends ASTQuickFix<ReturnStatement> {

    public UnnecessaryReturnQuickFix(final PMDMarker marker) {
        super(marker);
    }

    @Override
    protected ImageDescriptor getImageDescriptor() {
        return PMDPluginImages.QUICKFIX_REMOVE;
    }

    @Override
    public String getLabel() {
        return "Remove return";
    }

    @Override
    public String getDescription() {
        return "Removes the return statement.";
    }

    @Override
    protected NodeFinder<CompilationUnit, ReturnStatement> getNodeFinder(final Position position) {
        return Finders.positionWithinNode(position, getNodeType());
    }

    /**
     * Removes the return statement.
     */
    @Override
    protected boolean apply(final ReturnStatement node) {
        node.delete();
        return true;
    }

}
