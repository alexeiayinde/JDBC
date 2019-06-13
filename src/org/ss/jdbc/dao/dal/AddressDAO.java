package org.ss.jdbc.dao.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.ss.jdbc.dao.domain.Address;
import org.ss.jdbc.dao.domain.Contact;

public class AddressDAO {

	private static final String INSERT_QUERY = "INSERT INTO address (details) VALUES(?, ?)";
	private static final String UPDATE_QUERY = "UPDATE address SET details = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM address WHERE id = ?";
	private static final String SELECT_QUERY = "SELECT * FROM address WHERE id = ?";
	private static final String SELECT_QUERY_CONTACTS = "SELECT * FROM address INNER JOIN contact c ON ? = c.address_id";

	public void create(Address a) throws SQLException {
		Connection connection = PersistenceManager.getConnection();

		try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, a.getDetails());

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				while (rs.next()) {
					a.setId(rs.getLong("id"));
				}
			}
		}
	}

	public void update(Address a) throws SQLException {
		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
			ps.setString(1, a.getDetails());
			ps.setLong(2, a.getId());
			ps.executeUpdate();
		}
	}

	public void delete(Address a) throws SQLException {
		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
			ps.setLong(1, a.getId());
			ps.executeUpdate();
		}
	}

	public Address findById(Long id) throws SQLException {
		Address a = new Address();
		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					a.setId(id);
					a.setDetails(rs.getString("details"));
				}
			}
		}
		return a;
	}

	public void loadContacts(Address a) throws SQLException {
		Set<Contact> contacts = new HashSet<>();
		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY_CONTACTS)) {
			ps.setLong(1, a.getId());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ContactDAO dao = new ContactDAO();
					contacts.add(dao.findById(rs.getLong(3)));
				}
			}
		}
		a.setContacts(contacts);
	}
}
