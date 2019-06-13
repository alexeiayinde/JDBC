package org.ss.jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.ss.jdbc.dao.dal.PersistenceManager;

public class App {

	public static void main(String[] args) throws SQLException {

		Connection cnx = PersistenceManager.getConnection();

		// Connection connect = DBConnection.getSingle().getConnection();

		try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM contact")) {

			while (rs.next()) {
				System.out.println(rs.getString("email"));
			}

		} catch (SQLException e) {
			System.out.println("Attention : " + e.getMessage());
		}

		PersistenceManager.closeConnection();

	}

}
