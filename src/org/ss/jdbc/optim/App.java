package org.ss.jdbc.optim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/jdbc-book?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String pwd = "";

		try (Connection conn = DriverManager.getConnection(url, user, pwd);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM contact")) {

			while (rs.next()) {
				System.out.println("Une ligne");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
