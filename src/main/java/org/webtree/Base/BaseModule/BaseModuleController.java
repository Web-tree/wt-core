package org.webtree.Base.BaseModule;
 
import org.webtree.Base.MVC.BaseController;
import org.webtree.Base.MVC.BaseView;
import org.webtree.Language.Model.LanguageModel;
import org.webtree.System.Auth;
import org.webtree.System.Helpers.RequestHelper;

import java.util.List;

/**
 * @author lucifer
 *         Date: 07.05.12
 *         Time: 8:29
 */
abstract public class BaseModuleController extends BaseController {
	protected LanguageModel language;

	public LanguageModel getLanguage() {
		return language;
	}

	public void setLanguage(LanguageModel language) {
		this.language = language;
	}


	protected String getActionByParam(List<String> params) {
		try {
			return params.get(1);
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
	}

	protected BaseView.FORMAT getViewFormat() {
		BaseView.FORMAT format;
		String requestFormat = RequestHelper.getParam("format");
		if (requestFormat == null) {
			format = BaseView.FORMAT.HTML;
		}

		if (BaseView.FORMAT.JSON.toString().equals(requestFormat)) {
			format = BaseView.FORMAT.JSON;
		} else {
			format = BaseView.FORMAT.HTML;
		}
		return format;
	}

	protected boolean editAvailable(int humanId) {
		boolean editAvailable = false;
		try{
			editAvailable = Auth.getInst().getAuthModel().getHumanId() == humanId;
		} catch (Auth.AuthRequired ignored) {}
		return editAvailable;
	}

	abstract public String process(List<String> params);
}
