package org.webtree.Node.View;

import java.util.HashMap;

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
	protected HashMap<String, Object> getDefaultMap(Object object) {
		HashMap<String, Object> defaultMap = super.getDefaultMap(object);
		defaultMap.put("editable", editable);
		return defaultMap;
	}
}
