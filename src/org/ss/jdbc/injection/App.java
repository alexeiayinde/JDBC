package org.ss.jdbc.injection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		Statement statement = conn.createStatement();

		System.out.println("###### Boîte de login ######");
		System.out.println("Entrez votre login : ");
		String loginUser = sc.nextLine();
		System.out.println("Entrez votre mot de passe : ");
		String password = sc.nextLine();

		// ResultSet res = statement.executeQuery(
		// "SELECT * FROM contact WHERE email = '" + loginUser + "' AND first_name = '"
		// + password + "'"); Injection de SQL possible !! Ex : mettre
		// "alexei@alexei.org' -- " pour le login et n'importe quel mdp

		String query = "SELECT * FROM contact WHERE email = ? AND first_name = ?";
		PreparedStatement ps = conn.prepareStatement(query); // PreparedStatement permet d'empêcher les injections SQL
		ps.setString(1, loginUser);
		ps.setString(2, password);
		ResultSet res = ps.executeQuery();

		if (res.next()) {
			System.out.println("Bienvenue dans votre espace de travail " + res.getString("first_name") + " "
					+ res.getString("last_name"));
		} else {
			System.out.println("Erreur : login / mot de passe inccorects");
		}

		res.close();
		statement.close();
		conn.close();

	}

}
