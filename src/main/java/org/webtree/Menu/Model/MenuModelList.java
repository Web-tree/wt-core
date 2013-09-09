package org.webtree.Menu.Model;

import org.webtree.Base.MVC.BaseModelList;

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
//
//		if (Auth.getInst().isAuthorized()) {
//			try {
//				AuthModel authModel = Auth.getInst().getAuthModel();
//				menuList.add(new MenuModel("human/" + authModel.getHumanId(), "Я"));
//			} catch (Auth.AuthRequired authRequired) {
//				throw new LoggedError("Error with check auth!", authRequired);
//			}
//		} else {
//			menuList.add(new MenuModel("auth/login", "Вход"));
//			menuList.add(new MenuModel("auth/register", "Регистрация"));
//		}
//
//		menuList.add(new MenuModel("human/list", "Люди"));
//		menuList.add(new MenuModel("node", "Ноды"));

		menuList.add(new MenuModel("page/webTree", "Web-Tree"));
		menuList.add(new MenuModel("page/rootTeam", "Root team"));
		menuList.add(new MenuModel("page/rate", "Rate"));
		menuList.add(new MenuModel("page/worksnet", "Works-Net"));
		menuList.add(new MenuModel("page/freeIdea", "FreeIdea"));
		menuList.add(new MenuModel("form/feedback", "Обратная связь"));

		return menuList;
	}
}