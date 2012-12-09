package org.webtree.Site.PersonalPage;

import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.System.Auth;
import org.webtree.System.Exception.MessageException;
import org.webtree.System.Router;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lucifer
 *         Date: 18.10.12
 *         Time: 9:55
 */
public class PersonalPageController extends BaseModuleController{

	@Override
	public String process(List<String> params) throws Router.RedirectSystemError, IOException, Router.RedirectPageNotFound, Router.Redirect, SQLException, MessageException.ErrorMessage {
		PersonalPageView view;
		if (Auth.getInst().isAuthorized()){
			view = new PersonalPageViewMain();
		} else {
			view = new PersonalPageViewNotLogin();
		}
		return view.parse();
	}
}
