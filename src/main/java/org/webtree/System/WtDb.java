package org.webtree.System;

import org.webtree.System.Exception.LoggedError;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author lucifer
 *         Date: 28.04.12
 *         Time: 18:47
 */
public class WtDb {
	protected static Connection connect;
	protected static int transact = 0;

	public static Connection getConnect() {
		if (connect == null) {
			initConnect();
		}
		return connect;
	}

	public static void transact() throws SQLException {
		if (transact < 1) {
			connect.setAutoCommit(false);
		}
		transact++;
	}

	public static void commit() throws SQLException {
		connect.commit();
		transact--;
		if (transact < 1) {
			connect.setAutoCommit(true);
		}
	}

	public static void rollback() {
		try {
			connect.rollback();
			transact--;
			if (transact < 1) {
				connect.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new SqlError(e);
		}
	}

	protected static void initConnect() {
		System.setProperty("jdbc.drivers", "org.postgresql.Driver");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new LoggedError("Class 'org.postgresql.Driver' not found.", e);
		}
		Properties properties = new Properties();
		properties.setProperty("user", ProjectSettings.get("pgUser"));
		properties.setProperty("password", ProjectSettings.get("pgPass"));
		try {
			connect = DriverManager.getConnection(ProjectSettings.get("pgConnect"), properties);
		} catch (SQLException e) {
			Log.getInst().debug(e.getMessage());
			throw new LoggedError("Can't connect to DB.", e);
		}
	}

	public static class SqlError extends Error{
		public SqlError(Throwable cause) {
			super(cause);
		}
	}
}
