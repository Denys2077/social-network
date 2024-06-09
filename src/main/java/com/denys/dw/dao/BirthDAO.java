package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.connect.ConnectorDB;
import com.denys.dw.user.addition.Birth;
import com.denys.dw.user.addition.Sex;

public class BirthDAO implements AbstractDAO<Birth> {

	@Override
	public Birth findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public Birth findEntityById(long id) {
		final String QUERY = "SELECT * FROM birth WHERE user_id = ?";
		Birth birth = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				birth = new Birth();
				birth.setSex(Sex.valueOf(res.getString("sex")));
				birth.setDate(res.getDate("date").toLocalDate());
				birth.setUser_id(res.getLong("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return birth;
	}

	@Override
	public boolean createEntityByParameter(Birth param) {
		return false;
	}

	@Override
	public boolean updateEntityByParameter(Birth param) {
		final String QUERY = "UPDATE birth SET sex = ?, date = ? WHERE user_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, String.valueOf(param.getSex()));
			stat.setObject(2, param.getDate());
			stat.setLong(3, param.getUser_id());
			
			int execute = stat.executeUpdate();
			return execute > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean deleteEntityByParameter(Birth param) {
		final String QUERY = "DELETE FROM birth WHERE user_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getUser_id());
			return stat.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Birth> getAllEntities() {
		List<Birth> list = new ArrayList<>();
		final String QUERY = "SELECT * FROM birth";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			Birth birth;
			while(res.next()) {
				birth = new Birth();
				birth.setSex(Sex.valueOf(res.getString("sex")));
				birth.setDate(res.getDate("date").toLocalDate());
				birth.setUser_id(res.getLong("user_id"));
				list.add(birth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<Birth> getAllEntitiesById(long id) {
		return null;
	}

	@Override
	public Birth findEntityByObject(Birth obj) {
		return null;
	}

}
