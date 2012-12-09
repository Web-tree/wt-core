package org.webtree.System;

import org.webtree.System.Exception.ItemNotFound;

import javax.servlet.http.Cookie;

/**
 * @author lucifer
 *         Date: 27.06.12
 *         Time: 22:29
 */
public class WtCookie {
	protected static int defaultLifeTime = 30 * 24 * 60 * 60;

	public static Cookie getCookie(String name) throws CookieNotFound {
		Cookie[] cookies = Registry.getInst()
		.getRequest()
		.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		throw new CookieNotFound();
	}

	public static String getCookieValue(String name) throws CookieNotFound {
		return getCookie(name).getValue();
	}

	public static void setCookie(String name, String value) {
		setCookie(name, value, defaultLifeTime, ProjectSettings.get("baseHost"));
	}

	public static void setCookie(String name, String value, String domain) {
		setCookie(name, value, defaultLifeTime, domain);
	}

	public static void setCookie(String name, String value, int maxAge) {
		setCookie(name, value, maxAge, ProjectSettings.get("baseHost"));
	}

	public static void setCookie(String name, String value, int maxAge, String domain) {
		Log.getInst().debug(String.valueOf(maxAge));
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
//		cookie.setDomain(domain);
		cookie.setPath("/");
		setCookie(cookie);
	}

	public static void setCookie(Cookie cookie) {
		Registry.getInst().getResponse().addCookie(cookie);
	}

	public static class CookieNotFound extends ItemNotFound {
	}
}
