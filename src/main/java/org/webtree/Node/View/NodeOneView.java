package org.webtree.Node.View;

import java.util.Map;

/**
 * @author lucifer
 *         Date: 10.07.12
 *         Time: 9:14
 */
public class NodeOneView extends NodeView {
	@Override
	protected String templateName() {
		return "one";
	}

	@Override
	protected String templateDirectory() {
		return "node";
	}

	@Override
	protected Map<String, Object> getDefaultMap() {
		Map<String, Object> defaultMap = super.getDefaultMap();
		return defaultMap;
	}
}
