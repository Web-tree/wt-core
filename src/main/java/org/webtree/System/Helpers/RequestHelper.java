package org.webtree.System.Helpers;

import org.webtree.System.Registry;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lucifer
 *         Date: 03.07.12
 *         Time: 23:07
 */
public class RequestHelper {
	public static Map<String, String[]> getParams() {
		return getRequest().getParameterMap();
	}

	public static String getParam(String name) {
		return getRequest().getParameter(name);
	}

	public static boolean paramExists(String name) {
		return null != getParam(name);
	}

	public static boolean isAjax() {
		try {
			return getRequest().getHeader("HTTP_X_REQUESTED_WITH").toLowerCase().equals("xmlhttprequest");
		} catch (NullPointerException e) {
			return false;
		}
	}

	protected static HttpServletRequest getRequest() {
		return Registry.getInst().getRequest();
	}
}
