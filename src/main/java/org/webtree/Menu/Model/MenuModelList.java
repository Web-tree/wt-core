package org.webtree.Menu.Model;

import org.webtree.Auth.Model.AuthModel;
import org.webtree.Base.MVC.BaseModelList;
import org.webtree.System.Auth;
import org.webtree.System.Exception.LoggedError;

/**
 * @author lucifer
 *         Date: 31.05.12
 *         Time: 23:42
 */
public class MenuModelList extends BaseModelList<MenuModel> {

	/**
	 * Return new menuList with items
	 *
	 * @return MenuModelList
	 */
	public static MenuModelList getMenu() {
		MenuModelList menuList = new MenuModelList();

		if (Auth.getInst().isAuthorized()) {
			try {
				AuthModel authModel = Auth.getInst().getAuthModel();
				menuList.add(new MenuModel("human/" + authModel.getHumanId(), "Я"));
			} catch (Auth.NeedAuth needAuth) {
				throw new LoggedError("Error with check auth!", needAuth);
			}
		} else {
			menuList.add(new MenuModel("auth/login", "Вход"));
			menuList.add(new MenuModel("auth/register", "Регистрация"));
		}

		menuList.add(new MenuModel("human/list", "Люди"));
		menuList.add(new MenuModel("node", "Ноды"));
		return menuList;
	}
}