package org.webtree.Human.Controller;

import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.Human.DAO.HumanDAO;
import org.webtree.Human.Model.HumanModel;
import org.webtree.Human.View.HumanEditView;
import org.webtree.Human.View.HumanListView;
import org.webtree.Human.View.HumanOneView;
import org.webtree.Human.View.HumanView;
import org.webtree.Link.Model.LinkModel;
import org.webtree.Node.DAO.NodeDAO;
import org.webtree.System.Exception.ItemNotFound;
import org.webtree.System.Exception.MessageException;
import org.webtree.System.Registry;
import org.webtree.System.Router;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lucifer
 * Date: 16.04.12
 * Time: 13:13
 */
public class HumanController extends BaseModuleController {
	protected static HumanDAO humanDAO;

	public HumanController() {
		humanDAO = new HumanDAO();
	}

	public int create(String name) {
		HumanModel humanModel = new HumanModel(name);
		return humanDAO.create(humanModel);
	}

	public void edit(HumanModel humanModel) throws SQLException {
		humanDAO.edit(humanModel);
	}

	public HumanModel getHuman(int id) throws SQLException, ItemNotFound {
		return humanDAO.get(id);
	}

	public void delete(int id) throws SQLException {
		humanDAO.delete(id);
	}

	@Override
	public String process(List<String> params) {
		HumanView view = null;
		try {
			switch (getActionByParam(params)) {
				case "":
					throw new Router.Redirect(new LinkModel("human/list").buildUrl());
				case "list":
					view = new HumanListView(getViewFormat());
					view.setData(humanDAO.getHumanList());
					break;
				case "save":
					Registry.getInst().getRequestParam("");
					break;
				case "edit":
					view = new HumanEditView(getViewFormat());
				default:
					if (view == null){
						view = new HumanOneView(getViewFormat());
					}
					int humanId = Integer.parseInt(params.get(1));
					Map<String, Object> content = new HashMap<>();
					HumanModel humanModel = humanDAO.get(humanId);
					content.put("user", humanModel);

					NodeDAO nodeDAO = new NodeDAO();

					try {
						content.put("nodes", nodeDAO.getHumanList(humanModel.getId()));
					} catch (NodeDAO.NodeException e) {
						throw new Error(e);
					}
					view.setData(content);
					view.setEditable(editAvailable(humanId));
			}
		} catch (ItemNotFound e){
			view.addMessage(new MessageException.ErrorMessage(e.getMessage()));
		}
		return view.parse();
	}
}
