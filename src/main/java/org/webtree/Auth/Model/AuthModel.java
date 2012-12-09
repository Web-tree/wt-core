package org.webtree.Auth.Model;

/**
 * @author lucifer
 *         Date: 29.06.12
 *         Time: 21:43
 */
public class AuthModel extends AbstractAuthModel {
	protected int loginId;
	protected String email;
	protected int humanId;

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getHumanId() {
		return humanId;
	}

	public void setHumanId(int humanId) {
		this.humanId = humanId;
	}

	@Override
	public boolean registered() {
		return true;
	}
}