package org.webtree.System.Helpers;

import org.webtree.Auth.Model.AuthModel;
import org.webtree.Link.Model.LinkModel;
import org.webtree.System.Auth;
import org.webtree.System.ProjectSettings;
import org.webtree.System.Registry;

/**
 * @author lucifer
 *         Date: 01.07.12
 *         Time: 23:21
 */
public class ViewHelper {
	public boolean hasAuth() {
		return Auth.getInst().isAuthorized();
	}

	public AuthModel getAuthModel() {
		try {
			return (AuthModel) Auth.getInst().getAuthData();
		} catch (ClassCastException e) {
			return null;
		}
	}

	public String getParam(String name) {
		return Registry.getInst().getRequest().getParameter(name);
	}

	public String getLinkUrl(String controller, String action) {
		return link(controller, action).buildUrl();
	}

	public String buildHtmlLink(String controller, String action, String title) {
		return link(controller, action, title).buildHtmlLink();
	}

	public LinkModel link(String controller) {
		return link(controller, null);
	}

	public LinkModel link(String controller, String action) {
		return LinkModel.getLink(controller, action);
	}

	public LinkModel link(String controller, String action, String title) {
		return LinkModel.getLink(controller, action, title);
	}

	public String getLogHost() {
		return ProjectSettings.get("logHost");
	}

	public String getBaseUrl() {
		return ProjectSettings.get("baseUrl");
	}

}
