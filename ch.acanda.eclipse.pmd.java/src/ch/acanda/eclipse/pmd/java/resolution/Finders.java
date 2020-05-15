package ch.acanda.eclipse.pmd.java.resolution;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jface.text.Position;

/**
 * Static utility methods pertaining to {@link NodeFinder} instances.
 *
 * @author Philip Graf
 */
public final class Finders {

    private Finders() {
        // hide constructor of utility class
    }

    /**
     * Creates a {@link NodeFinder} that finds a node depending on its type and a position that encloses the node.
     *
     * @param nodeType The type of the node.
     * @param position The position that encloses the node completely.
     */
    public static <R extends ASTNode, N extends ASTNode> NodeFinder<R, N> nodeWithinPosition(final Class<? extends N> nodeType,
            final Position position) {
        return new NodeWithinPositionNodeFinder<R, N>(position, nodeType);
    }

    /**
     * Creates a {@link NodeFinder} that finds a node depending on its type and a position that lies within the node.
     *
     * @param nodeType The type of the node.
     * @param position The position that lies within the node completely.
     */
    @SafeVarargs
    public static <R extends ASTNode, N extends ASTNode> NodeFinder<R, N> positionWithinNode(final Position position,
            final Class<? extends N>... nodeTypes) {
        return new PositionWithinNodeNodeFinder<R, N>(position, nodeTypes);
    }

}
