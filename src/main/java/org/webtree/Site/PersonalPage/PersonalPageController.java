package org.webtree.site.PersonalPage;

import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.System.Auth;

import java.util.List;

/**
 * @author lucifer
 *         Date: 18.10.12
 *         Time: 9:55
 */
public class PersonalPageController extends BaseModuleController{

	@Override
	public String process(List<String> params) {
		PersonalPageView view;
		if (Auth.getInst().isAuthorized()){
			view = new PersonalPageViewMain();
		} else {
			view = new PersonalPageViewNotLogin();
		}
		return view.parse();
	}
}
