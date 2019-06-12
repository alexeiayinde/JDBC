package org.ss.jdbc.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/jdbc-book?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String pwd = "";

		Connection conn = DriverManager.getConnection(url, user, pwd);

		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getTables(conn.getCatalog(), null, null, null);
		System.out.println("***** LISTE DES TABLES DE LA BDD *****");
		while (rs.next()) {
			System.out.print(rs.getString("TABLE_NAME") + " - ");
		}
		System.out.println();

		String response;
		Statement statement = conn.createStatement();
		do {
			System.out.print("Entrez le nom de la table : ");
			response = sc.nextLine();

			if (!response.equals("exit")) {
				ResultSet result = statement.executeQuery("SELECT * FROM " + response);
				ResultSetMetaData rsMetaData = result.getMetaData();

				System.out.println("Voici les informations de la table " + response);
				System.out.println(
						"\n***************************************************************************************************************************");
				for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
					System.out.printf("%-25s", "| " + rsMetaData.getColumnName(i).toUpperCase() + "["
							+ rsMetaData.getColumnTypeName(i) + "]");
				}
				System.out.println(
						"\n***************************************************************************************************************************");

				while (result.next()) {
					for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
						System.out.printf("%-25s", "| " + result.getObject(i).toString());
					}
					System.out.println(
							"\n---------------------------------------------------------------------------------------------------------------------------");
				}

				result.close();
			}

		} while (!response.equalsIgnoreCase("exit"));

		statement.close();
		rs.close();
		conn.close();

	}

}
