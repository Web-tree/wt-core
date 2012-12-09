package org.webtree.Link.Model;

import org.webtree.System.ProjectSettings;
import org.webtree.System.Registry;

import java.net.MalformedURLException;

/**
 * @author lucifer
 *         Date: 02.06.12
 *         Time: 15:42
 */
public class LinkModel {
	protected String path;
	protected String title;
	protected String target;

	public static LinkModel getLink(String controller) {
		return getLink(controller, null);
	}

	/**
	 * @param controller String
	 * @param action     String|null
	 * @return LinkModel
	 */
	public static LinkModel getLink(String controller, String action) {
		String path = controller;
		if (action != null) {
				path += "/" + action;
			}
		return new LinkModel(path);
	}

	public static LinkModel getLink(String controller, String action, String title) {
		LinkModel link = getLink(controller, action);
		link.setTitle(title);
		return link;
	}

	public LinkModel(String path) {
		this.path = path;
	}

	public LinkModel setTitle(String title) {
		this.title = title;
		return this;
	}

	public LinkModel setTarget(String target) {
		this.target = target;
		return this;
	}

	public LinkModel(String path, String title) {
		setData(path, title, null);
	}

	public LinkModel(String path, String title, String target) throws MalformedURLException {
		setData(path, title, target);
	}

	protected void setData(String path, String title, String target) {
		this.path = path;
		this.title = title;
		this.target = target;
	}


	public String buildUrl() {
		return buildUrl(path);
	}

	public static String buildUrl(String path) {
		return ProjectSettings.get("baseUrl") + Registry.getInst().getLanguage().getLocale().getLanguage() + "/" + path;
	}

	public String buildHtmlLink(String title) {
		this.title = title;
		return buildHtmlLink();
	}

	public String buildHtmlLink() {
		String target = (this.target == null ? "" : this.target);
		return "<a href=" + buildUrl() + target + ">" + title + "</a>";
	}
}