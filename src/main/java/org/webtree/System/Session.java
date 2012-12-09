package org.webtree.System;

/**
 * @author lucifer
 *         Date: 26.06.12
 *         Time: 19:17
 */
public class Session {
	public static Object getAttribute(String name) {
		return Registry.getInst().getRequest().getSession(true).getAttribute(name);
	}

	public static void setAttribute(String name, Object value) {
		Registry.getInst()
		.getRequest()
		.getSession(true)
		.setAttribute(name, value);
	}
}
