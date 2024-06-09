package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.connect.ConnectorDB;
import com.denys.dw.user.addition.Address;

public class AddressDAO implements AbstractDAO<Address>{

	@Override
	public Address findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public Address findEntityById(long id) {
		final String QUERY = "SELECT * FROM address WHERE user_id = ?";
		Address address = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				address = new Address();
				address.setCountry(res.getString("country"));
				address.setCity(res.getString("city"));
				address.setStreet(res.getString("street"));
				address.setUser_id(res.getLong("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}

	@Override
	public boolean createEntityByParameter(Address param) {
		return false;
	}

	@Override
	public boolean updateEntityByParameter(Address param) {
		final String QUERY = "UPDATE address SET country = ?, city = ?, street = ? WHERE user_id = ?";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, param.getCountry());
			stat.setString(2, param.getCity());
			stat.setString(3, param.getStreet());
			stat.setLong(4, param.getUser_id());
			int execute = stat.executeUpdate();
			return execute > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(Address param) {
		final String QUERY = "DELETE FROM address WHERE user_id = ?";
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
	public List<Address> getAllEntities() {
		final String QUERY = "SELECT * FROM address";
		List<Address> list = new ArrayList<>();
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			Address address;
			while(res.next()) {
				address = new Address();
				address.setCountry(res.getString("country"));
				address.setCity(res.getString("city"));
				address.setStreet(res.getString("street"));
				address.setUser_id(res.getLong("user_id"));
				list.add(address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Address> getAllEntitiesById(long id) {
		return null;
	}

	@Override
	public Address findEntityByObject(Address obj) {
		return null;
	}

}
