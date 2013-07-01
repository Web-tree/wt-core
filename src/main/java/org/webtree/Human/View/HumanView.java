package org.webtree.Human.View;

import org.webtree.Base.BaseModule.BaseModuleView;
import org.webtree.Base.BaseModule.Editable;

/**
 * User: Lucifer
 * Date: 16.04.12
 * Time: 13:23
 */
abstract public class HumanView extends BaseModuleView implements Editable {
	protected boolean editable = false;

	public HumanView(FORMAT format) {
		super(format);
	}
	@Override
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
