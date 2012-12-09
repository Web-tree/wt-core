package org.webtree.Human.View;

/**
 * @author lucifer
 *         Date: 01.09.12
 *         Time: 14:42
 */
public class HumanListView extends HumanView {
	@Override
	protected String templateName() {
		return "list";
	}

	@Override
	protected String templateDirectory() {
		return "human";
	}

	public HumanListView(FORMAT format) {
		super(format);
	}
}