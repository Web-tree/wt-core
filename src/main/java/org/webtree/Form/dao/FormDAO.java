package org.webtree.form.dao;

import org.webtree.Base.DAO.SqlDAO;
import org.webtree.System.Router;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Max Levicky
 *         Date: 04.08.13
 *         Time: 17:14
 */
public class FormDAO extends SqlDAO {
	protected String insertStatement = "INSERT INTO feedback (theme, email, text) VALUES (?, ?, ?)";

	public void addFeedback(String theme, String email, String text) {
		PreparedStatement statement = getStatement(insertStatement);
		try {
			statement.setString(1, theme);
			statement.setString(2, email);
			statement.setString(3, text);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new Router.RedirectSystemError(e);
		}
	}
}
