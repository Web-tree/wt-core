package org.webtree.Menu.Model;

import org.webtree.Base.MVC.BaseModel;
import org.webtree.Link.Model.LinkModel;

/**
 * @author lucifer
 *         Date: 31.05.12
 *         Time: 10:49
 */
public class MenuModel extends BaseModel {
	protected LinkModel link;
	protected MenuModelList subMenu;

	public MenuModel(String path, String title) {
		link = new LinkModel(path, title);
	}

	public MenuModelList getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(MenuModelList subMenu) {
		this.subMenu = subMenu;
	}

	public boolean hasSub() {
		return subMenu != null;
	}

	public String buildHtmlLink() {
		return link.buildHtmlLink();
	}
}