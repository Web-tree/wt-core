package org.webtree.Node.DAO;

import org.webtree.Base.DAO.SqlDAO;
import org.webtree.Node.Model.NodeModel;
import org.webtree.Node.Model.NodeModelList;
import org.webtree.System.Exception.ItemNotFound;
import org.webtree.System.Exception.LoggedError;
import org.webtree.System.Exception.WebTreeException;
import org.webtree.System.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * @author lucifer
 *         Date: 29.04.12
 *         Time: 15:23
 */
public class NodeDAO extends SqlDAO {
	protected String createStatement = "INSERT INTO node (\"ownerId\", title) VALUES (?, ?)";
	protected String editStatement = "UPDATE node SET %s WHERE id = ?";
	protected String getStatement = "SELECT * FROM node WHERE \"nodeId\" IN (?)";
	protected String delStatement = "DELETE FROM node WHERE id IN (?)";
	protected String activeNodeCount = "SELECT COUNT(*) FROM node WHERE active = true";
	protected String setRate = "UPDATE node SET rate = ? WHERE \"nodeId\" = ?";

	protected int nodesOnPage = 10;

	public int create(NodeModel nodeModel) throws NodeException {
		try {
			nodeModel.validate();
			PreparedStatement statement = getStatement(createStatement, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, nodeModel.getOwnerId());
			Log.getInst().debug(nodeModel.getTitle());
			statement.setString(2, nodeModel.getTitle());
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (NodeModel.ValidationError | SQLException e) {
			Log.getInst().debug("create node error", e);
			throw new NodeException();
		}
	}

	public void edit(NodeModel nodeModel) throws SQLException {
		String updateSet = buildUpdateSet(nodeModel.toHashMap());
		String sql = String.format(editStatement, updateSet);
		PreparedStatement statement = getStatement(sql);

		statement.setInt(1, nodeModel.getId());

		statement.execute();
	}

	public NodeModel get(int id) throws SqlDAOException, NodeNotFound {
		PreparedStatement statement = null;
		try {
			statement = getStatement(getStatement);
			statement.setInt(1, id);

			ResultSet resultSet = statement.executeQuery();

			if (!resultSet.next()) {
				throw new NodeNotFound();
			}
			return buildModel(resultSet);
		} catch (SQLException e) {
			throw new SqlDAOException(e);
		}

	}

	public NodeModelList getList() throws NodeException {
		return getList(1);
	}

	public NodeModelList getList(int page) throws NodeException {
		try {
			int offset = (page - 1) * nodesOnPage;
			String sql = "SELECT * FROM node LIMIT " + nodesOnPage + "OFFSET " + offset;

			return buildModelList(getStatement(sql));
		} catch (SQLException | SqlDAOException e) {
			Log.getInst().debug("asd", e);
			throw new NodeException();
		}
	}

	public NodeModelList getHumanList(int humanId) throws NodeException {
		return getHumanList(humanId, 1);
	}

	public NodeModelList getHumanList(int humanId, int page) throws NodeException {
		try {
			int offset = getOffset(page, nodesOnPage);
			String sql = "SELECT * FROM node WHERE \"ownerId\" = ? ORDER BY rate, creation, \"nodeId\" DESC LIMIT " + nodesOnPage + "OFFSET " + offset;
			PreparedStatement statement = getStatement(sql);
			statement.setInt(1, humanId);
			return buildModelList(statement);
		} catch (SQLException | SqlDAOException e) {
			Log.getInst().debug("qwe", e);
			throw new NodeException();
		}
	}

	public void setRate(int nodeId, int rate) throws NodeException {
		try {
			PreparedStatement statement = getStatement(setRate);
			statement.setInt(1, rate);
			statement.setInt(2, nodeId);
			statement.execute();
		} catch (SQLException e) {
			Log.getInst().error("Set rate SQL exception", e);
			throw new NodeException();
		}
	}

	public void delete(int id) throws SQLException {
		PreparedStatement statement = getStatement(delStatement);
		statement.setInt(1, id);
		statement.execute();
	}

	protected NodeModelList buildModelList(PreparedStatement statement) throws SqlDAOException {
		ResultSet result = null;
		try {
			result = statement.executeQuery();
			NodeModelList list = new NodeModelList();

			while (result.next()) {
				list.add(buildModel(result));
			}
			return list;
		} catch (SQLException e) {
			throw new SqlDAOException(e);
		}

	}

	protected NodeModel buildModel(ResultSet resultSet) throws SqlDAOException {
		try {
			NodeModel nodeModel = new NodeModel();
			nodeModel.setId(Integer.parseInt(resultSet.getString("nodeId")));
			nodeModel.setOwnerId(resultSet.getInt("ownerId"));
			try {
				nodeModel.setTitle(resultSet.getString("title"));
			} catch (NodeModel.NodeModelError nodeModelError) {
				throw new LoggedError(nodeModelError);
			}
			nodeModel.setRate(resultSet.getInt("rate"));
			try {
				nodeModel.setTitle(resultSet.getString("title"));
			} catch (NodeModel.NodeModelError nodeModelError) {
				throw new LoggedError(nodeModelError);
			}
			return nodeModel;
		} catch (SQLException e) {
			throw new SqlDAOException(e);
		}
	}


	public HashSet<Integer> getRandomNodeIds() {
		return getRandomNodeIds(10);
	}

	public HashSet<Integer> getRandomNodeIds(int limit) {
		int activeCount = getActiveCount();
		int offset = (int) ((activeCount - limit) * Math.random());
		if (offset < 1) {
			offset = 0;
		}
		try {
			ResultSet resultSet = getConnect().prepareStatement("SELECT \"nodeId\" FROM node WHERE active = TRUE LIMIT " + limit + " OFFSET " + offset).executeQuery();

			HashSet<Integer> ids = new HashSet<Integer>();
			while (resultSet.next()) {
				ids.add(resultSet.getInt(1));
			}
			return ids;
		} catch (SQLException e) {
			throw new LoggedError("Get random node ids SQL exception", e);
		}
	}

	public int getActiveCount() {
		try {
			ResultSet result = getStatement(activeNodeCount).executeQuery();
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			throw new LoggedError("Active node count SQL exception", e);
		}
	}

	public static class NodeException extends WebTreeException {
	}
	public static class NodeNotFound extends ItemNotFound {
		public NodeNotFound() {
			super("Нод не найден.");
		}
	}
}