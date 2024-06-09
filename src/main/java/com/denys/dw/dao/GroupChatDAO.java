package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.connect.ConnectorDB;

public class GroupChatDAO implements AbstractDAO<GroupChat> {

	@Override
	public GroupChat findEntityByTwoParameters(String paramOne, String paramTwo) {
		List<GroupChat> list = new ArrayList<>();
		final String QUERY = "SELECT * FROM group_chat WHERE creater_id = ? AND name_group = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, Long.parseLong(paramOne));
			stat.setString(2, paramTwo);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				GroupChat chat = new GroupChat(res.getLong(1), res.getLong(2), res.getString(3), res.getBlob(4), res.getLong(5));
			    list.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (list.size() > 0) ? list.get(0) : null;
	}

	@Override
	public GroupChat findEntityById(long id) {
		final String QUERY = "SELECT * FROM group_chat WHERE id = ?";
		GroupChat chat = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				chat = new GroupChat(res.getLong(1), res.getLong(2), res.getString(3), res.getBlob(4), res.getLong(5));;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chat;
	}

	@Override
	public GroupChat findEntityByObject(GroupChat obj) {
		final String QUERY = "SELECT * FROM group_chat WHERE id = ? AND creater_id = ? AND name_group = ? AND connected_user_id = ?";
		GroupChat chat = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, obj.getId());
			stat.setLong(2, obj.getCreater_id());
			stat.setString(3, obj.getName_group());
			stat.setLong(4, obj.getConnected_user_id());
			
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				chat = new GroupChat(res.getLong(1), res.getLong(2), res.getString(3), res.getBlob(4), res.getLong(5));;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chat;
	}

	@Override
	public boolean createEntityByParameter(GroupChat param) {
		final String QUERY = "INSERT INTO group_chat (creater_id, name_group, photo_group, connected_user_id) VALUES (?, ?, ?, ?)";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getCreater_id());
			stat.setString(2, param.getName_group());
			stat.setBlob(3, param.getGroup_photo());
			stat.setLong(4, param.getConnected_user_id());
			
			int rowCount = stat.executeUpdate();
			return rowCount > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateEntityByParameter(GroupChat param) {
		final String QUERY = "UPDATE group_chat SET name_group = ?, photo_group = ? WHERE id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, param.getName_group());
			if(param.getGroup_photo() != null) {
				stat.setBlob(2, param.getGroup_photo());
			} else if(param.getInputStreamPhoto() != null) {
				stat.setBlob(2, param.getInputStreamPhoto());
			} else {
				 stat.setNull(2, Types.BLOB);
			}
			stat.setLong(3, param.getId());
			int execute = stat.executeUpdate();
			return execute > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(GroupChat param) {
		final String QUERY = "DELETE FROM group_chat WHERE creater_id = ? AND name_group = ? AND connected_user_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getCreater_id());
			stat.setString(2, param.getName_group());
			stat.setLong(3, param.getConnected_user_id());
			
			return stat.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<GroupChat> getAllEntities() {
		final String QUERY = "SELECT * FROM group_chat";
		List<GroupChat> list = new ArrayList<>();
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				GroupChat chat = new GroupChat(res.getLong(1), 
						                       res.getLong(2), 
						                       res.getString(3), 
						                       res.getBlob(4), 
						                       res.getLong(5));
				list.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<GroupChat> getAllEntitiesById(long id) {
		return null;
	}

}
