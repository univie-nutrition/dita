package dita.globodiet.survey.view;

import java.util.Optional;
import java.util.Stack;

import org.apache.causeway.applib.fa.FontAwesomeLayers;
import org.apache.causeway.applib.graph.tree.TreePath;
import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.valuetypes.asciidoc.applib.value.AsciiDoc;

import lombok.val;

/**
 * Represents a node in the tree.
 */
public record SurveyTreeNode(
        String title,
        String faQuickNotation,
        AsciiDoc content,
        TreePath path,
        Can<SurveyTreeNode> children) {

    public FontAwesomeLayers faLayers() {
        return FontAwesomeLayers.fromQuickNotation(faQuickNotation);
    }

    /**
     * Resolves given {@link TreePath} to its corresponding {@link SurveyTreeNode} if possible.
     */
    public static Optional<SurveyTreeNode> lookup(final SurveyTreeNode root, final TreePath treePath) {
        if(treePath.isRoot()) {
            return Optional.of(root);
        }

        val stack = new Stack<SurveyTreeNode>();
        stack.push(root);

        treePath.streamPathElements()
        .skip(1) // skip first path element which is always '0' and corresponds to the root, which we already handled above
        .forEach(pathElement->{
            if(stack.isEmpty()) return; // an empty stack corresponds to a not found state

            val currentNode = stack.peek();
            val child = currentNode.children().get(pathElement).orElse(null);

            if(child!=null) {
                stack.push(child);
            } else {
                stack.clear(); // not found
            }
        });

        return stack.isEmpty()
                ? Optional.empty()
                : Optional.of(stack.peek());
    }


}


