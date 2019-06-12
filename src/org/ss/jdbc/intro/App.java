package org.ss.jdbc.intro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

	public static void main(String[] args) throws ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver"); // Ceci est inutile depuis JDBC 4 !

		String url = "jdbc:mysql://localhost:3306/jdbc-book?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String mdp = "";

		try {
			Connection conn = DriverManager.getConnection(url, user, mdp);

			Statement statement = conn.createStatement();

			ResultSet result = statement.executeQuery("SELECT * FROM ?");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
