package org.webtree.Node.View;

/**
 * @author lucifer
 *         Date: 24.06.12
 *         Time: 18:50
 */
public class NodeCreateView extends NodeView {
	@Override
	protected String templateName() {
		return "create";
	}

	@Override
	protected String templateDirectory() {
		return "node";
	}
}
