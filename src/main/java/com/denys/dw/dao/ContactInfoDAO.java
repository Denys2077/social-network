package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.connect.ConnectorDB;
import com.denys.dw.user.addition.ContactInfo;

public class ContactInfoDAO implements AbstractDAO<ContactInfo> {

	@Override
	public ContactInfo findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public ContactInfo findEntityById(long id) {
		final String QUERY = "SELECT * FROM contactinfo WHERE user_id = ?";
		ContactInfo info = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				info = new ContactInfo();
				info.setEmail(res.getString("email"));
				info.setTelephone(res.getString("telephone"));
				info.setDepartment(res.getString("department"));
				info.setPosition(res.getString("position"));
				info.setUser_id(res.getInt("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public boolean createEntityByParameter(ContactInfo param) {
		return false;
	}

	@Override
	public boolean updateEntityByParameter(ContactInfo param) {
		final String QUERY = "UPDATE contactinfo SET email = ?, telephone = ?, position = ?, department = ? WHERE user_id = ?";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, param.getEmail());
			stat.setString(2, param.getTelephone());
			stat.setString(3, param.getPosition());
			stat.setString(4, param.getDepartment());
			stat.setLong(5, param.getUserId());
			
			int execute = stat.executeUpdate();
			return execute > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean deleteEntityByParameter(ContactInfo param) {
		final String QUERY = "DELETE FROM contactinfo WHERE user_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getUserId());
			return stat.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<ContactInfo> getAllEntities() {
		final String QUERY = "SELECT * FROM contactinfo";
		List<ContactInfo> list = new ArrayList<>();
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			ContactInfo info;
			while(res.next()) {
				info = new ContactInfo();
				info.setEmail(res.getString("email"));
				info.setTelephone(res.getString("telephone"));
				info.setDepartment(res.getString("department"));
				info.setPosition(res.getString("position"));
				info.setUser_id(res.getInt("user_id"));
				list.add(info);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return list;
	}

	@Override
	public List<ContactInfo> getAllEntitiesById(long id) {
		return null;
	}

	@Override
	public ContactInfo findEntityByObject(ContactInfo obj) {
		return null;
	}

}
