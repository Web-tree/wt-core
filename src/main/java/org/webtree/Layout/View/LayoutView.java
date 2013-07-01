package org.webtree.Layout.View;

import org.webtree.Base.BaseModule.HtmlView;
import org.webtree.Menu.Model.MenuModelList;

import java.util.Map;

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
	protected Map<String, Object> getDefaultMap() {
		Map<String, Object> data = super.getDefaultMap();
		data.put("menu", MenuModelList.getMenu());
		return data;
	}

	@Override
	public String parse() {
		return parseView();
	}
}
