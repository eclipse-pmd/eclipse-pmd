package ch.acanda.eclipse.pmd.java.resolution;

import java.util.Optional;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Implementations of this interface are used to find a node of an AST which matches implementation specific criteria.
 * @param R The type of the root node from which the AST will be traversed.
 * @param N The type of the node for which this finder is looking.
 */
public interface NodeFinder<R extends ASTNode, N extends ASTNode> {

    /**
     * @return A node from the provided AST if a matching node could be found.
     */
    Optional<N> findNode(R ast);

}
