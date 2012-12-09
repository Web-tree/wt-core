package org.webtree.Layout.Controller;

import org.webtree.Base.MVC.BaseController;
import org.webtree.Layout.View.LayoutView;
import org.webtree.Menu.Model.MenuModelList;

/**
 * @author lucifer
 *         Date: 13.05.12
 *         Time: 21:16
 */
public class LayoutController extends BaseController {
	public String parse(String content) {
		LayoutView view = new LayoutView();
		view.setData(content);
		view.setMenu(MenuModelList.getMenu());
		return view.parse();
	}
}