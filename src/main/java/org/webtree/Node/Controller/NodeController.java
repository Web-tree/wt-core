package org.webtree.Node.Controller;

import org.webtree.Base.BaseModule.BaseModuleController;
import org.webtree.Base.DAO.SqlDAO;
import org.webtree.Link.Model.LinkModel;
import org.webtree.Node.DAO.NodeDAO;
import org.webtree.Node.Model.NodeModel;
import org.webtree.Node.Model.NodeModelList;
import org.webtree.Node.View.NodeCreateView;
import org.webtree.Node.View.NodeListView;
import org.webtree.Node.View.NodeOneView;
import org.webtree.Node.View.NodeView;
import org.webtree.System.Auth;
import org.webtree.System.Exception.ItemNotFound;
import org.webtree.System.Exception.MessageException;
import org.webtree.System.Helpers.RequestHelper;
import org.webtree.System.Router;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Lucifer
 *         Date: 16.04.12
 *         Time: 21:58
 */
public class NodeController extends BaseModuleController {
	protected static NodeDAO nodeDAO;

	protected static String defaultAction = "list";

	public NodeController() {
		nodeDAO = new NodeDAO();
	}

	public int create(int ownerId) throws NodeDAO.NodeException {
		NodeModel nodeModel = new NodeModel(ownerId);
		return nodeDAO.create(nodeModel);
	}

	public void edit(NodeModel nodeModel) throws SQLException {
		nodeDAO.edit(nodeModel);
	}

	public NodeModel getNode(int id) throws ItemNotFound, SqlDAO.SqlDAOError {
		return nodeDAO.get(id);
	}

	public void delete(int id) throws SQLException {
		nodeDAO.delete(id);
	}

	@Override
	public String process(List<String> params) {
		String action = "";
		try {
			action = params.get(1);
		} catch (IndexOutOfBoundsException e) {
			Router.redirect(LinkModel.getLink("node", defaultAction));
		}
		NodeView view;
		switch (action) {
			case "list":
				String listMode;
				try {
					listMode = params.get(2);
				} catch (IndexOutOfBoundsException e) {
					listMode = "";
				}
				view = new NodeListView();
				try {
					NodeModelList nodes;
					switch (listMode) {
						case "my":
							nodes = nodeDAO.getHumanList(Auth.getInst().getAuthModel().getHumanId());
							break;
						case "":
						default:
							nodes = nodeDAO.getList();
					}
					view.setData(nodes);
				} catch (NodeDAO.NodeException e) {
					view.addMessage(new MessageException.ErrorMessage("Ошибка при получении списка."));
				} catch (Auth.AuthRequired authRequired) {
					view.addMessage(new MessageException.WarningMessage("Для того, что бы посмотреть свои ноды, надо представиться."));
				}
				break;
			case "create":
				view = new NodeCreateView();
				if (null != RequestHelper.getParam("create")) {
					try {
						Auth.getInst().needAuth();
						view.setData(actionCreate());
						view.addMessage(new MessageException.InfoMessage("Нод успешно создан."));
					} catch (CreateError | Auth.AuthRequired e) {
						view.addMessage(e);
					}
				}
				break;
			case "edit":
				view = new NodeCreateView();
				break;
			case "delete":
				view = new NodeCreateView();
				break;
			case "one":
				view = new NodeOneView();
				try {
					NodeModel nodeModel = nodeDAO.get(Integer.parseInt(params.get(2)));
					view.setData(nodeModel);
					view.setEditable(editAvailable(nodeModel.getOwnerId()));
				} catch (SqlDAO.SqlDAOError e) {
					view.addMessage(new MessageException.ErrorMessage("Произошла ошибка."));
				} catch (ItemNotFound itemNotFound) {
					view.addMessage(new MessageException.ErrorMessage(itemNotFound.getMessage()));
				}
				break;
			default:
				throw new Router.Redirect(LinkModel.getLink("node", defaultAction).buildUrl());
		}
		return view.parse();
	}

	public NodeModel actionCreate() throws CreateError, Auth.AuthRequired {
		NodeModel nodeModel = new NodeModel();
		nodeModel.setOwnerId(Auth.getInst().getAuthModel().getHumanId());
		try {
			nodeModel.setTitle((String) RequestHelper.getParam("title"));
		} catch (NodeModel.NodeModelError nodeModelError) {
			throw new CreateError(nodeModelError.getMessage());
		}
		try {
			nodeModel.setId(nodeDAO.create(nodeModel));
		} catch (NodeDAO.NodeException e) {
			throw new CreateError();
		}

		return nodeModel;
	}

	protected class CreateError extends MessageException.ErrorMessage {
		public CreateError(String message) {
			super(message);
		}

		public CreateError() {
			super("Произошла ошибка при создании нода");
		}
	}
}