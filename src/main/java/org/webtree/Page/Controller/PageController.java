package org.webtree.Page.Controller;

import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.Page.DAO.PageDAO;
import org.webtree.Page.View.PageView;
import org.webtree.System.Router;

import java.util.HashMap;
import java.util.List;

/**
 * User: Lucifer
 * Date: 16.04.12
 * Time: 13:13
 */
public class PageController extends BaseModuleController {
	protected PageDAO pageDAO;
	protected HashMap<String, PageView> pageView = new HashMap<>();

	protected static final String defaultPageName = "main";

	protected enum AvailablePages{main, about, currentStage, rootTeam, freeIdea, rate, worksnet}

	public PageController() {
		pageDAO = new PageDAO();
	}

	@Override
	public String process(List<String> params) {
//		PageModel pageModel;

//		try {
//			pageModel = pageDAO.getFirstPage();
////		} catch (SQLException e) {
////			Log.getInst().trace("{}", e);
////			throw new Router.RedirectSystemError();
//		} catch (ItemNotFound e) {
//			Log.getInst().debug(e.getMessage());
//			throw new Router.RedirectPageNotFound();
//		}

		String page;
		try{
			page = params.get(1);
		} catch (IndexOutOfBoundsException e) {
			page = defaultPageName;
		}

		PageView view = getPageView(page);



//		view.setModel(pageModel);
		return view.parse();
	}

	protected PageView getPageView(String templateName) throws Router.RedirectPageNotFound {
		try{
			AvailablePages.valueOf(AvailablePages.class, templateName);
		} catch (IllegalArgumentException e){
			throw new Router.RedirectPageNotFound();
		}
		if (!pageView.containsKey(templateName)) {
			pageView.put(templateName, new PageView(getViewFormat(), templateName));
		}
		return pageView.get(templateName);
	}
}