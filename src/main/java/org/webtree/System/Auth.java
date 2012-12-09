package org.webtree.System;

import org.webtree.Auth.Model.AbstractAuthModel;
import org.webtree.Auth.Model.AnonymousModel;
import org.webtree.Auth.Model.AuthModel;
import org.webtree.Base.DAO.SqlDAO;
import org.webtree.System.Exception.LoggedError;
import org.webtree.System.Exception.MessageException;
import org.webtree.System.Exception.WebTreeException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * @author lucifer
 *         Date: 27.06.12
 *         Time: 8:45
 */
public class Auth extends SqlDAO {
	protected static Auth inst;

	public static String sessionAttrName = "authData";
	final public static String authCookieName = "token";

	public static Auth getInst() {
		if (inst == null) {
			inst = new Auth();
		}
		return inst;
	}

	public void initAuth() {
		String token = null;
		try {
			token = WtCookie.getCookieValue(authCookieName);
			if ((null != token) || !checkSession()) {
				authByToken(token);
			}
		} catch (WtCookie.CookieNotFound | WrongToken e) {
			AnonymousModel anonymousData = new AnonymousModel();
			if (token == null) {
				token = genNewToken();
			}
			anonymousData.setToken(token);
			WtCookie.setCookie(authCookieName, anonymousData.getToken());
			Session.setAttribute(sessionAttrName, anonymousData);
		}
	}

	public void needAuth() throws NeedAuth {
		if (!this.isAuthorized()) {
			throw new NeedAuth();
		}
	}

	public void authByToken(String token) throws WrongToken {
		try {
			PreparedStatement statement = getStatement(
			"SELECT l.email, l.\"loginId\", l.\"humanId\" " +
			"FROM auth.token AS t " +
			"LEFT JOIN auth.login AS l " +
			"ON t.\"loginId\" = l.\"loginId\" " +
			"WHERE token = ?"
			);
			statement.setString(1, token);
			ResultSet result = statement.executeQuery();

			if (!result.next()) {
				throw new WrongToken();
			}

			startSession(result.getString("email"), result.getInt("loginId"), result.getInt("humanId"));
		} catch (SQLException e) {
			Log.getInst().debug("authByToken sql error", e);
			throw new WrongToken(e);
		}
	}

	public boolean isAuthorized() {
		return getAuthData().registered();
	}

	public AbstractAuthModel getAuthData() {
		return (AbstractAuthModel) Session.getAttribute(sessionAttrName);
	}

	public AuthModel getAuthModel() throws NeedAuth {
		if (!isAuthorized()) {
			throw new NeedAuth();
		}
		return (AuthModel) Session.getAttribute(sessionAttrName);
	}

	public void auth(String login, String pass) throws SQLException, WrongAuthData {
		PreparedStatement statement;
		try {
			statement = getStatement("SELECT \"humanId\", \"loginId\" FROM auth.login WHERE email = ? AND password = ?");
		} catch (SQLException e) {
			Log.getInst().debug(e.getMessage());
			throw new LoggedError(e);
		}

		statement.setString(1, login);
		statement.setString(2, pass);

		ResultSet result = statement.executeQuery();

		if (!result.next()) {
			throw new WrongAuthData();
		}

		startSession(login, result.getInt("loginId"), result.getInt("humanId"));
	}

	public void startSession(String login, int loginId, int humanId) throws SQLException {
		AuthData authData = new AuthData();
		authData.setToken(getToken());
		authData.setLoginId(loginId);
		authData.setEmail(login);
		authData.setHumanId(humanId);

		setToken(authData.getToken(), authData.getLoginId());
		setAuthData(authData);
		WtCookie.setCookie(authCookieName, authData.getToken());
	}

	protected boolean checkSession() {
		return null != Session.getAttribute(sessionAttrName);
	}

	protected void setAuthData(AuthData authData) {
		Session.setAttribute(sessionAttrName, authData);
	}

	protected void setToken(String token, int humanId) throws SQLException {
		PreparedStatement insertStatement = getStatement("INSERT INTO auth.token (token, \"loginId\") VALUES (?, ?)");
		insertStatement.setString(1, token);
		insertStatement.setInt(2, humanId);
		try {
			insertStatement.execute();
		} catch (SQLException e) {
			if (e.getSQLState().equals(SQLSTATE_DUPLICATE)) {
				PreparedStatement updateStatement = getStatement("UPDATE auth.token SET \"loginId\" = ? WHERE token = ?");
				updateStatement.setInt(1, humanId);
				updateStatement.setString(2, token);
				updateStatement.executeUpdate();
			} else {
				throw e;
			}
		}
	}

	protected static String getToken() {
		String token;
		try {
			token = WtCookie.getCookie(authCookieName).getValue();
		} catch (WtCookie.CookieNotFound cookieNotFound) {
			token = genNewToken();
		}
		return token;
	}

	protected static String genNewToken() {
		Random rand = new Random();
		String text = String.valueOf(rand.nextInt() | System.nanoTime());
		Log.getInst().debug("Token rand text {}", text);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new LoggedError("Auth error", e);
		}
		digest.update(text.getBytes());
		byte[] result = digest.digest();
		StringBuilder sb = new StringBuilder();
		for (byte aResult : result) {
			sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public static class WrongAuthData extends WebTreeException {
	}

	protected class AuthData extends AuthModel {

	}

//	protected class AnonymousData extends AuthModel {}

	protected class WrongToken extends WebTreeException {
		public WrongToken() {
		}

		public WrongToken(Throwable t) {
			super(t);
		}
	}

	public static class NeedAuth extends MessageException.ErrorMessage {
		public NeedAuth() {
			super("Требуется авторизация");
		}
	}
}