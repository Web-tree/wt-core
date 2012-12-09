package org.webtree.Auth.View;

import org.webtree.Base.BaseModule.BaseModuleView;

/**
 * @author lucifer
 *         Date: 29.06.12
 *         Time: 21:47
 */
abstract public class AuthView extends BaseModuleView {
	@Override
	protected String templateDirectory() {
		return "auth";
	}
}
