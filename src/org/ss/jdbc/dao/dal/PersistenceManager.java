package org.ss.jdbc.dao.dal;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PersistenceManager {

	private static final int CHECK_CONNECTION_TIMEOUT = 10;
	private static Connection connection;

	private PersistenceManager() {
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || !connection.isValid(CHECK_CONNECTION_TIMEOUT) || connection.isClosed()) {

			Properties props = new Properties();
			try (FileInputStream fis = new FileInputStream("resources/conf.properties")) {
				props.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String url = props.getProperty("ds.jdbc.url");
			String login = props.getProperty("ds.user.login");
			String pwd = props.getProperty("ds.user.pwd");
			connection = DriverManager.getConnection(url, login, pwd);
		}
		return connection;
	}

	public static void closeConnection() throws SQLException {
		if (connection != null && !connection.isClosed())
			connection.close();
	}
}
