package org.webtree.Auth.Model;

/**
 * @author lucifer
 *         Date: 30.06.12
 *         Time: 19:26
 */
public class AnonymousModel extends AbstractAuthModel {
	@Override
	public boolean registered() {
		return false;
	}
}
