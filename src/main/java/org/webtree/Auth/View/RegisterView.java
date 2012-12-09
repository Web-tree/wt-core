package org.webtree.Auth.View;

import java.util.HashMap;

/**
 * @author lucifer
 *         Date: 29.06.12
 *         Time: 21:47
 */
public class RegisterView extends AuthView{
	public void setData(HashMap<String, String> data) {
		super.setData(data);
	}

	@Override
	protected String templateName() {
		return "register";
	}
}
