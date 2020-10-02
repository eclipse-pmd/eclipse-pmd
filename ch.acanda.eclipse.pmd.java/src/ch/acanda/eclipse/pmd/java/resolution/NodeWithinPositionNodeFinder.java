package ch.acanda.eclipse.pmd.java.resolution;

import java.util.Optional;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.text.Position;

/**
 * Searches an AST for a node that has the provided type and lies within the provided position. If more than one node
 * fit the criteria, the one with the smallest distance to the root is returned.
 */
class NodeWithinPositionNodeFinder<R extends ASTNode, N extends ASTNode> extends ASTVisitor implements NodeFinder<R, N> {

    private final int start;
    private final int end;
    private final Class<? extends N> nodeType;
    private N node;

    public NodeWithinPositionNodeFinder(final Position position, final Class<? extends N> nodeType) {
        start = position.getOffset();
        end = start + position.getLength();
        this.nodeType = nodeType;
    }

    @Override
    public boolean preVisit2(final ASTNode node) {
        if (this.node != null) {
            return false;
        }
        final int nodeStart = node.getStartPosition();
        final int nodeEnd = nodeStart + node.getLength();
        if (start <= nodeStart && nodeEnd <= end) {
            if (nodeType.isAssignableFrom(node.getClass())) {
                this.node = nodeType.cast(node);
                return false;
            }
            return true;
        }
        return nodeStart <= start && end <= nodeEnd;
    }

    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public Optional<N> findNode(final R ast) {
        node = null;
        ast.accept(this);
        return Optional.ofNullable(node);
    }

}
