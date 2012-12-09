package org.webtree.Human.DAO;

import org.webtree.Base.DAO.SqlDAO;
import org.webtree.Human.Model.HumanModel;
import org.webtree.Human.Model.HumanModelList;
import org.webtree.System.Exception.ItemNotFound;
import org.webtree.System.Exception.LoggedError;
import org.webtree.System.Exception.WebTreeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lucifer
 *         Date: 28.04.12
 *         Time: 19:40
 */
public class HumanDAO extends SqlDAO {
	protected String createStatement = "INSERT INTO human (name) VALUES (?)";
	protected String editStatement = "UPDATE human SET %s WHERE \"humanId\" = ?";
	protected String getStatement = "SELECT * FROM human WHERE \"humanId\" IN (?)";
	protected String getByEmailStatement = "SELECT * FROM human WHERE \"humanId\" = ?";
	protected String delStatement = "DELETE FROM human WHERE \"humanId\" IN (?)";
	protected String getRateStatement = "SELECT rate FROM human WHERE \"humanId\" = ?";

	protected int peopleOnPage = 50;

	public int create(HumanModel humanModel) throws SQLException {
		PreparedStatement statement = getStatement(createStatement, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, humanModel.getName());
		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		return resultSet.getInt(1);
	}

	public void edit(HumanModel humanModel) throws SQLException {
		String updateSet = buildUpdateSet(humanModel.toHashMap());
		String sql = String.format(editStatement, updateSet);
		PreparedStatement statement = getStatement(sql);

		statement.setInt(1, humanModel.getId());

		statement.execute();
	}

	public HumanModel get(int id) throws ItemNotFound {
		try{
			PreparedStatement statement = getStatement(getStatement);
			statement.setInt(1, id);

			return buildModel(statement);
		} catch (SQLException e) {
			throw new LoggedError(e);
		}
	}

	public HumanModel getByEmail(String email) throws ItemNotFound {
		try {
			PreparedStatement statement = getStatement(getByEmailStatement);
			statement.setString(1, email);
			return buildModel(statement);
		} catch (SQLException e) {
			throw new LoggedError(e);
		}
	}

	protected HumanModel buildModel(PreparedStatement statement) throws HumanNotFound {
		try {
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				throw new HumanNotFound();
			}

			HumanModel humanModel = new HumanModel();
			humanModel.setId(resultSet.getInt("humanId"));
			humanModel.setName(resultSet.getString("name"));
			return humanModel;
		} catch (SQLException e) {
			throw new LoggedError(e);
		}
	}

	public HumanModelList getHumanList() {
		return getHumanList(1);
	}

	public HumanModelList getHumanList(int page) {
		try {
			PreparedStatement statement = getStatement("SELECT * FROM human WHERE hidden = false ORDER BY rate LIMIT " + peopleOnPage + " OFFSET " + getOffset(page, peopleOnPage));
			HumanModelList humanList = new HumanModelList();
			fillModelList(humanList, statement);
			return humanList;
		} catch (SQLException | SqlDAOException e) {
			throw new LoggedError(e);
		}
	}

	public void delete(int id) throws SQLException {
		PreparedStatement statement = getStatement(delStatement);
		statement.setInt(1, id);
		statement.execute();
	}

	public int getHumanRate(int humanId) throws ItemNotFound {
		try {
			PreparedStatement statement = getStatement(getRateStatement);
			statement.setInt(1, humanId);
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				throw new HumanNotFound();
			}
			return resultSet.getInt("rate");
		} catch (SQLException e) {
			throw new LoggedError(e);
		}
	}

	@Override
	protected HumanModel buildModel(ResultSet resultSet) throws SqlDAOException {
		try {
			HumanModel humanModel = new HumanModel();
			humanModel.setId(Integer.parseInt(resultSet.getString("humanId")));
			humanModel.setName(resultSet.getString("name"));
			humanModel.setRate(resultSet.getInt("rate"));
			return humanModel;
		} catch (SQLException e) {
			throw new SqlDAOException(e);
		}
	}

	public static class HumanDAOException extends WebTreeException {
		public HumanDAOException(String message) {
			super(message);
		}
	}

	public static class HumanNotFound extends ItemNotFound {
		public HumanNotFound() {
			super("Человек не найден");
		}
	}
}
