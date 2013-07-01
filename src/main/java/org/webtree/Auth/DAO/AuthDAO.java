package org.webtree.Auth.DAO;

import org.webtree.Auth.Model.AuthModel;
import org.webtree.Base.DAO.SqlDAO;
import org.webtree.System.Exception.WebTreeException;
import org.webtree.System.Helpers.FormatHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lucifer
 *         Date: 30.06.12
 *         Time: 14:04
 */
public class AuthDAO extends SqlDAO {
	public AuthModel createLogin(String login, String password, int humanId) throws LoginExists, LoginIncorrect, SqlDAOError {
		checkLogin(login);
		try {
			PreparedStatement statement = getStatement("INSERT INTO auth.email(email, password, \"humanId\") VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, login);
			statement.setString(2, password);
			statement.setInt(3, humanId);
			statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();

			AuthModel authModel = new AuthModel();
			authModel.setLoginId(resultSet.getInt("loginId"));
			authModel.setEmail(login);
			authModel.setHumanId(humanId);
			return authModel;
		} catch (SQLException e) {
			if (e.getSQLState().equals(SQLSTATE_DUPLICATE)) {
				throw new LoginExists();
			} else {
				throw new SqlDAOError(e);
			}
		}
	}

	public void checkLogin(String login) throws LoginIncorrect, LoginExists, SqlDAOError {
		try {
			FormatHelper.assertLogin(login);
		} catch (FormatHelper.LoginAssertFail loginAssertFail) {
			throw new LoginIncorrect();
		}

		if (checkLoginExists(login)) {
			throw new LoginExists();
		}
	}

	public boolean checkLoginExists(String login) throws SqlDAOError {
		try {
			PreparedStatement statement = getStatement("SELECT 1 FROM auth.login WHERE email = ?");
			statement.setString(1, login);
			return statement.executeQuery().next();
		} catch (SQLException e) {
			throw new SqlDAOError(e);
		}
	}

	abstract public static class AuthException extends WebTreeException {
	}

	public static class LoginExists extends AuthException {
		@Override
		public String getDefaultMessage() {
			return "Такой логин уже существует.";
		}
	}

	public static class LoginIncorrect extends AuthException {
		@Override
		public String getDefaultMessage() {
			return "Такой логин использовать нельзя.";
		}
	}
}
