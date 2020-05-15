package ch.acanda.eclipse.pmd.java.resolution.design;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Position;

import ch.acanda.eclipse.pmd.java.resolution.ASTQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.Finders;
import ch.acanda.eclipse.pmd.java.resolution.NodeFinder;
import ch.acanda.eclipse.pmd.marker.PMDMarker;
import ch.acanda.eclipse.pmd.ui.util.PMDPluginImages;

/**
 * Quick fix for the rule
 * <a href="http://pmd.sourceforge.net/rules/java/unnecessary.html#UselessOverridingMethod">UselessOverridingMethod</a>.
 * It removes the useless method.
 */
public class UselessOverridingMethodQuickFix extends ASTQuickFix<MethodDeclaration> {

    public UselessOverridingMethodQuickFix(final PMDMarker marker) {
        super(marker);
    }

    @Override
    protected ImageDescriptor getImageDescriptor() {
        return PMDPluginImages.QUICKFIX_REMOVE;
    }

    @Override
    public String getLabel() {
        return "Remove method";
    }

    @Override
    public String getDescription() {
        return "Removes the method.";
    }

    @Override
    protected NodeFinder<CompilationUnit, MethodDeclaration> getNodeFinder(final Position position) {
        return Finders.positionWithinNode(position, getNodeType());
    }

    /**
     * Removes the method.
     */
    @Override
    protected boolean apply(final MethodDeclaration node) {
        node.delete();
        return true;
    }

}
