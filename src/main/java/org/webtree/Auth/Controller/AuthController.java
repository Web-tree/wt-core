package org.webtree.Auth.Controller;

import org.webtree.Auth.DAO.AuthDAO;
import org.webtree.Auth.View.AuthView;
import org.webtree.Auth.View.LoginView;
import org.webtree.Auth.View.MainView;
import org.webtree.Auth.View.RegisterView;
import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.Human.Controller.HumanController;
import org.webtree.System.*;
import org.webtree.System.Exception.MessageException;
import org.webtree.System.Helpers.RequestHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @author lucifer
 *         Date: 29.06.12
 *         Time: 22:49
 */
public class AuthController extends BaseModuleController {
	protected AuthDAO authDAO;

	@Override
	public String process(List<String> params) throws Router.RedirectSystemError, IOException, Router.RedirectPageNotFound, Router.Redirect, SQLException, MessageException.ErrorMessage {
		AuthView view;
		switch (getActionByParam(params)) {
			case "register":
				view = new RegisterView();
				if (null != Registry.getInst().getRequestParam("register")) {
					try {
						String email = Registry.getInst().getRequestParam("email");
						String password = Registry.getInst().getRequestParam("pass");
						int humanId = register(email, password);
					} catch (AuthDAO.LoginIncorrect | AuthDAO.LoginExists e) {
						MessageException loginIncorrect = new MessageException.ErrorMessage(e.getMessage()).setName("loginIncorrect");
						view.addMessage(loginIncorrect);
					}
				}
				HashMap<String, String> formParams = new HashMap<String, String>();
				formParams.put("email", Registry.getInst().getRequestParam("email"));
				formParams.put("pass", Registry.getInst().getRequestParam("pass"));
				view.setData(formParams);
				break;
			case "login":
				view = new LoginView();
				try {
					login();
				} catch (LoginFail e) {
					view.addMessage(e);
				}
				break;
			case "":
				view = new MainView();
				break;
			default:
				throw new Router.RedirectPageNotFound();
		}

		return view.parse();
	}

	public void login() throws LoginFail {
		try {
			if (RequestHelper.paramExists("login")){
				Auth.getInst().auth((String) RequestHelper.getParam("email"), (String) RequestHelper.getParam("pass"));
			}
		} catch (SQLException | Auth.WrongAuthData e) {
			Log.getInst().debug("login error", e);
			throw new LoginFail();
		}
	}

	public int register(String email, String password) throws SQLException, MessageException.ErrorMessage, AuthDAO.LoginIncorrect, AuthDAO.LoginExists {
		getAuthDAO().checkLogin(email);

		HumanController humanController = new HumanController();
		try {
			WtDb.transact();

			int humanId = humanController.create(email);

			int loginId = getAuthDAO().createLogin(email, password, humanId).getLoginId();
			WtDb.commit();
			try {
				Auth.getInst().startSession(email, loginId, humanId);
			} catch (SQLException e) {
				throw new Error("Start session error after register", e);
			}
			return humanId;
		} catch (AuthDAO.LoginExists | AuthDAO.LoginIncorrect e) {
			WtDb.rollback();
			throw new MessageException.ErrorMessage(e.getMessage());
		}
	}

	protected AuthDAO getAuthDAO() {
		if (authDAO == null) {
			authDAO = new AuthDAO();
		}
		return authDAO;
	}

	protected static class LoginFail extends MessageException.ErrorMessage {

		public LoginFail() {
			super("Не удалось выполнить вход. Возможно Вы ввели неверное имя пользователя и/или пароль?");
		}
	}
}
