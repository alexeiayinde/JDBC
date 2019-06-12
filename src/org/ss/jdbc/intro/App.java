package org.ss.jdbc.intro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

	public static void main(String[] args) throws SQLException {

		Connection conn = null;
		Statement statement = null;
		ResultSet result = null;
		ResultSet result2 = null;

		// Class.forName("com.mysql.cj.jdbc.Driver"); // Ceci est inutile depuis JDBC 4

		String url = "jdbc:mysql://localhost:3306/jdbc-book?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String pwd = "";

		try {
			conn = DriverManager.getConnection(url, user, pwd);

			statement = conn.createStatement();

			// statement.executeUpdate("INSERT INTO address (details) VALUES('10 rue
			// Crébillon, 44000 Nantes')",
			// Statement.RETURN_GENERATED_KEYS);

			// result = statement.getGeneratedKeys();

			// if (result.next()) {
			// statement.executeUpdate(
			// "INSERT INTO contact (email, first_name, last_name, address_id) VALUES
			// ('alex@alex.org', 'Alexei', 'Ay',"
			// + result.getLong(1) + ")");
			// }

			result2 = statement.executeQuery("SELECT * FROM contact c INNER JOIN address a ON c.address_id = a.id");

			while (result2.next()) {
				System.out.println(result2.getString("first_name") + " " + result2.getString("last_name") + " - "
						+ result2.getString("email") + " - " + result2.getString("details"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
			statement.close();
			// result.close();
			result2.close();

		}

	}

}
