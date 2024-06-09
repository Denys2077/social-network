package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.connect.ConnectorDB;
import com.denys.dw.user.contact.Contact;

public class ContactDAO implements AbstractDAO<Contact>{

	@Override
	public Contact findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public Contact findEntityById(long id) {
		return null;
	}

	@Override
	public boolean createEntityByParameter(Contact param) {
		final String QUERY = "INSERT INTO contact (current_id, friend_id) VALUES (?, ?)";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getCurrent_user_id());
			stat.setLong(2, param.getFriend_id());
			int executeRows = stat.executeUpdate();
			if(executeRows > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateEntityByParameter(Contact param) {
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(Contact param) {
		final String QUERY = "DELETE FROM contact WHERE current_id = ? AND  friend_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getCurrent_user_id());
			stat.setLong(2, param.getFriend_id());
			return stat.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Contact> getAllEntities() {
		List<Contact> contacts = new ArrayList<>();
		final String QUERY = "SELECT * FROM contact";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				Contact contact = new Contact(res.getLong(1), res.getLong(2), res.getLong(3));
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contacts;
	}

	@Override
	public List<Contact> getAllEntitiesById(long id) {
		List<Contact> contacts = new ArrayList<>();
		final String QUERY = "SELECT * FROM contact WHERE current_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				Contact contact = new Contact(res.getLong(1), res.getLong(2), res.getLong(3));
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contacts;
	}

	@Override
	public Contact findEntityByObject(Contact obj) {
		return null;
	}

}
