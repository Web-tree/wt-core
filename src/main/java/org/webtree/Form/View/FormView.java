package org.webtree.form.view;

import org.webtree.Base.BaseModule.BaseModuleView;

/**
 * @author Max Levicky
 *         Date: 04.08.13
 *         Time: 15:39
 */
public class FormView extends BaseModuleView {

	public FormView(FORMAT format) {
		super(format);
	}

	@Override
	protected String templateName() {
		return "feedback";
	}

	@Override
	protected String templateDirectory() {
		return "form";
	}
}
