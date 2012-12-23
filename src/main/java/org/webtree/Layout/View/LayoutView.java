package org.webtree.Layout.View;

import org.webtree.Base.BaseModule.HtmlView;
import org.webtree.Menu.Model.MenuModelList;

import java.util.HashMap;

/**
 * @author lucifer
 *         Date: 13.05.12
 *         Time: 21:20
 */
public class LayoutView extends HtmlView {
	protected MenuModelList menu;

	public MenuModelList getMenu() {
		return menu;
	}

	public void setMenu(MenuModelList menu) {
		this.menu = menu;
	}

	@Override
	protected String templateName() {
		return "main";
	}

	@Override
	protected String templateDirectory() {
		return "../layout";
	}

	@Override
	protected HashMap<String, Object> getDefaultMap(Object object) {
		HashMap<String, Object> data = super.getDefaultMap(object);
		data.put("menu", MenuModelList.getMenu());
		return data;
	}

	@Override
	public String parse() {
		return parseView();
	}
}
