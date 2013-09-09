package org.webtree.form.controller;

import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.System.Log;
import org.webtree.System.Registry;
import org.webtree.System.Router;
import org.webtree.form.dao.FormDAO;
import org.webtree.form.view.FormView;

import java.util.HashMap;
import java.util.List;

/**
 * @author Max Levicky
 *         Date: 04.08.13
 *         Time: 15:23
 */
public class FormController extends BaseModuleController {
	private FormDAO dao = new FormDAO();

	@Override
	public String process(List<String> params) {
		String viewName;
		try {
			viewName = params.get(1);
		} catch (IndexOutOfBoundsException e) {
			throw new Router.RedirectPageNotFound();
		}
		FormView view;
		if (viewName.equals("feedback")) {
			view = new FormView(getViewFormat());
		} else {
			throw new Router.RedirectPageNotFound();
		}

		Registry registry = Registry.getInst();
		HashMap<String, String> data = new HashMap<>();
		Log.getInst().debug(registry.getRequestParam("submit"));
		if (registry.getRequestParam("submit") != null) {
			String theme = registry.getRequestParam("theme");
			String email = registry.getRequestParam("email");
			String text = registry.getRequestParam("text");
			if (!theme.equals("") || !email.equals("") || !text.equals("")) {
				dao.addFeedback(
						theme,
						email,
						text);
				data.put("result", "1");
			}
			else {
				data.put("result", "2");
			}
		} else {
			data.put("theme", registry.getRequestParam("theme", ""));
			data.put("email", registry.getRequestParam("email", ""));
			data.put("text", registry.getRequestParam("text", ""));
		}
		view.setData(data);

		return view.parse();
	}
}
