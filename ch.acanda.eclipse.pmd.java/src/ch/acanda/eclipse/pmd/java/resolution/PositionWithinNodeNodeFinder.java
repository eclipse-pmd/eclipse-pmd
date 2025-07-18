package ch.acanda.eclipse.pmd.java.resolution;

import java.util.Optional;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.text.Position;

/**
 * Searches an AST for a node that has the provided type and includes the provided position. If more than one node fit
 * the criteria, the one with the largest distance to the root is returned.
 */
class PositionWithinNodeNodeFinder<R extends ASTNode, N extends ASTNode> extends ASTVisitor implements NodeFinder<R, N> {

    private final int start;
    private final int end;
    private final Class<? extends N>[] nodeTypes;
    @SuppressWarnings("PMD.SingularField")
    private N node;

    @SafeVarargs
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public PositionWithinNodeNodeFinder(final Position position, final Class<? extends N>... nodeTypes) {
        start = position.getOffset();
        end = start + position.getLength();
        this.nodeTypes = nodeTypes;
    }

    @Override
    public boolean preVisit2(final ASTNode node) {
        final int nodeStart = node.getStartPosition();
        final int nodeEnd = nodeStart + node.getLength();
        if (nodeStart <= start && end <= nodeEnd) {
            for (final Class<? extends N> nodeType : nodeTypes) {
                if (nodeType.isAssignableFrom(node.getClass())) {
                    this.node = nodeType.cast(node);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Optional<N> findNode(final R ast) {
        node = null;
        ast.accept(this);
        return Optional.ofNullable(node);
    }

}
