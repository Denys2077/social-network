package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.chat.ChatTwoUsers;
import com.denys.dw.connect.ConnectorDB;

public class ChatTwoUsersDAO implements AbstractDAO<ChatTwoUsers> {

	@Override
	public ChatTwoUsers findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public ChatTwoUsers findEntityById(long id) {
		ChatTwoUsers chat = null;
		final String QUERY = "SELECT * FROM chattwousers WHERE id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				chat = new ChatTwoUsers(res.getLong(1), res.getLong(2), res.getLong(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chat;
	}

	@Override
	public boolean createEntityByParameter(ChatTwoUsers param) {
		final String QUERY = "INSERT INTO chattwousers (first_user_id, second_user_id) VALUES (?, ?)";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getFirst_user_id());
			stat.setLong(2, param.getSecond_user_id());
			int rowCount = stat.executeUpdate();
			if(rowCount > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateEntityByParameter(ChatTwoUsers param) {
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(ChatTwoUsers param) {
		final String QUERY = "DELETE FROM chattwousers WHERE id = ?";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getId());
			
			return stat.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<ChatTwoUsers> getAllEntities() {
		final String QUERY = "SELECT * FROM chattwousers";
		List<ChatTwoUsers> list = new ArrayList<>();
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				ChatTwoUsers chat = new ChatTwoUsers();
				chat.setId(res.getLong(1));
				chat.setFirst_user_id(res.getLong(2));
				chat.setSecond_user_id(res.getLong(3));
				list.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ChatTwoUsers> getAllEntitiesById(long id) {
		return null;
	}

	@Override
	public ChatTwoUsers findEntityByObject(ChatTwoUsers obj) {
		ChatTwoUsers chat = null;
		final String QUERY = "SELECT * FROM chattwousers WHERE first_user_id = ? AND second_user_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, obj.getFirst_user_id());
			stat.setLong(2, obj.getSecond_user_id());
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				chat = new ChatTwoUsers(res.getLong(1), res.getLong(2), res.getLong(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chat;
	}

}
