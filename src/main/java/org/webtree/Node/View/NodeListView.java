package org.webtree.Node.View;

/**
 * @author lucifer
 *         Date: 08.07.12
 *         Time: 23:56
 */
public class NodeListView extends NodeView {
    @Override
    protected String templateName() {
        return "list";
    }

    @Override
    protected String templateDirectory() {
        return "node";
    }
}
