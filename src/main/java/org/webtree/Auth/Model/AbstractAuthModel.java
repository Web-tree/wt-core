package org.webtree.Auth.Model;

import org.webtree.Base.MVC.BaseModel;

/**
 * @author lucifer
 *         Date: 30.06.12
 *         Time: 17:30
 */
abstract public class AbstractAuthModel extends BaseModel {
	public String token;

	abstract public boolean registered();

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
