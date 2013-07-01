package org.webtree.Human.View;

import org.webtree.Base.BaseModule.Editable;

/**
 * @author lucifer
 *         Date: 10.07.12
 *         Time: 9:14
 */
public class HumanOneView extends HumanView implements Editable {
	@Override
	protected String templateName() {
		return "one";
	}

	@Override
	protected String templateDirectory() {
		return "human";
	}

	public HumanOneView(FORMAT format) {
		super(format);
	}


}
