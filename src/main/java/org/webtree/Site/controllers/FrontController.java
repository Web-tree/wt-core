package org.webtree.site.controllers;

import org.webtree.Auth.Controller.AuthController;
import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.Base.MVC.BaseController;
import org.webtree.Human.Controller.HumanController;
import org.webtree.Language.Controller.LanguageController;
import org.webtree.Language.Model.LanguageModel;
import org.webtree.Node.Controller.NodeController;
import org.webtree.Page.Controller.PageController;
import org.webtree.System.Auth;
import org.webtree.System.Exception.LoggedError;
import org.webtree.System.Exception.MessageException;
import org.webtree.System.Exception.WebTreeException;
import org.webtree.System.Helpers.ArrayHelper;
import org.webtree.System.Log;
import org.webtree.System.Registry;
import org.webtree.System.Router;
import org.webtree.form.controller.FormController;
import org.webtree.site.PersonalPage.PersonalPageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author lucifer
 *         Date: 02.05.12
 *         Time: 8:03
 */
//@SuppressWarnings("ALL")
public class FrontController extends BaseController {
	protected LanguageModel language;
	protected BaseModuleController controller;
	protected List<String> availableControllers;

	protected List<String> path;

	public FrontController(HttpServletRequest request, HttpServletResponse response, List<String> path) {
		availableControllers = Arrays.asList("page", "node", "human", "activity");

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new LoggedError("Wrong encoding for request", e);
		}
		response.setContentType("text/html;charset=UTF-8");
		Registry.getInst().init(request, response);
		Auth.getInst().initAuth();

		this.path = path;
	}

	public void setController(String controllerName) throws WrongController {
		Log.getInst().debug(controllerName);
		switch (controllerName) {
			case "node":
				controller = new NodeController();
				break;
			case "human":
				controller = new HumanController();
				break;
			case "page":
				controller = new PageController();
				break;
			case "auth":
				controller = new AuthController();
				break;
			case "form":
				controller = new FormController();
				break;
//			case "activity":
//				controller = new ActivityController();
//				break;
			case "I":
				controller = new PersonalPageController();
				break;
			default:
				throw new WrongController();
		}
	}

	public void setDefaultController() {
		try {
			setController(availableControllers.get(0));
		} catch (WrongController e) {
			throw new LoggedError("FrontController.availableControllers zero element is empty", e);
		}
	}

	public String parsePage() throws Router.BaseRedirect, IOException, SQLException, MessageException.ErrorMessage {
		Registry registry = Registry.getInst();
//		Log.getInst().debug("Registry: " + registry);
		return controller.process(getParams());
	}

	public void process() throws Router.Redirect, Router.RedirectPageNotFound {
		try {
			defineLang();
			defineController(path.get(2));
		} catch (ActivityAction activityAction) {
			defineController("activity");
		} catch (ArrayIndexOutOfBoundsException e) {
			setDefaultController();
		}
		defineHuman();
	}

	public void destruct() {
		Registry.getInst().clear();
	}

	public LanguageModel getLanguage() {
		return language;
	}

	public void setLanguage(LanguageModel language) {
		this.language = language;
	}

	public BaseModuleController getController() {
		return controller;
	}

	protected void defineController(String controllerName) throws Router.RedirectPageNotFound {
		try {
			setController(controllerName);
			Log.getInst().debug("path {}", path);
		} catch (WrongController wrongController) {
			throw new Router.RedirectPageNotFound();
		}
	}

	protected void defineLang() throws Router.Redirect, ActivityAction {
		LanguageController languageController = new LanguageController();
		LanguageModel lang;
		try {
			String firstParam = path.get(1);
			if (firstParam.equals("activity")) {
				throw new ActivityAction();
			}
			lang = languageController.getLang(firstParam);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new Router.Redirect(languageController.getRequestLanguage().getLocale().getLanguage() + "/", HttpServletResponse.SC_MOVED_PERMANENTLY);
		} catch (LanguageController.LangNotExists langNotExists) {
			throw new Router.Redirect("/" + langNotExists.getLanguage().getLocale().getLanguage() + "/" + ArrayHelper.implode("/", (String[]) path.toArray()), HttpServletResponse.SC_MOVED_PERMANENTLY);
		}
		Registry.getInst().setLanguage(lang);
	}

	protected List<String> getParams() {
		return path.subList(2, path.size());
	}

	protected void defineHuman() {

	}

	public static class WrongController extends WebTreeException {
	}

	protected static class ActivityAction extends WebTreeException {
	}
}