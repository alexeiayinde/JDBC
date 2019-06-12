package org.ss.jdbc.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/jdbc-book?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String pwd = "";

		Connection conn = DriverManager.getConnection(url, user, pwd);

		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getTables(conn.getCatalog(), null, "c%", null);

		conn.close();

	}

}
