package org.ss.jdbc.dao.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.ss.jdbc.dao.domain.Address;
import org.ss.jdbc.dao.domain.Contact;

public class ContactDAO {

	private static final String INSERT_QUERY = "INSERT INTO contact (email, first_name, last_name, address_id) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE contact SET email = ?, first_name = ?, last_name = ?, address_id = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM contact WHERE id = ?";
	private static final String SELECT_QUERY = "SELECT * FROM contact WHERE id = ?";

	public void create(Contact c) throws SQLException {

		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(INSERT_QUERY,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, c.getEmail());
			ps.setString(2, c.getFirstName());
			ps.setString(3, c.getLastName());
			Address address = c.getAddress();
			if (address != null) {
				if (address.getId() == null) {
					AddressDAO addressDAO = new AddressDAO();
					addressDAO.create(address);
				}
				ps.setLong(4, address.getId());
			} else {
				ps.setNull(4, Types.BIGINT);
			}
			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					c.setId(rs.getLong("id"));
			}
		}
	}

	public void update(Contact c) throws SQLException {
		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {
			ps.setString(1, c.getEmail());
			ps.setString(2, c.getFirstName());
			ps.setString(3, c.getLastName());
			ps.setLong(5, c.getId());
			Address address = c.getAddress();
			if (address != null) {
				if (address.getId() == null) {
					AddressDAO addressDAO = new AddressDAO();
					addressDAO.create(address);
				}
				ps.setLong(4, address.getId());
			}
			ps.executeUpdate();
		}
	}

	public void delete(Contact c) throws SQLException {
		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
			ps.setLong(1, c.getAddress().getId());
			ps.executeUpdate();
		}
	}

	public Contact findById(Long id) throws SQLException {
		Contact c = new Contact();
		Connection connection = PersistenceManager.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(SELECT_QUERY)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.first()) {
					c.setId(id);
					c.setEmail(rs.getString("email"));
					c.setFirstName(rs.getString("first_name"));
					c.setLastName(rs.getString("last_name"));
					AddressDAO dao = new AddressDAO();
					c.setAddress(dao.findById(rs.getLong("address_id")));
				}
			}
		}
		return c;
	}

}
