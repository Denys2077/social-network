package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.denys.dw.chat.message.MessageGroup;
import com.denys.dw.connect.ConnectorDB;

public class MessageGroupDAO implements AbstractDAO<MessageGroup>, MessageDB{

	@Override
	public MessageGroup findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public MessageGroup findEntityById(long id) {
		return null;
	}

	@Override
	public MessageGroup findEntityByObject(MessageGroup obj) {
		return null;
	}

	@Override
	public boolean createEntityByParameter(MessageGroup param) {
		final String QUERY = "INSERT INTO message_group (group_chat_id, sender_id, message, message_file, message_time) VALUES (?, ?, ?, ?, ?)";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getChat_id());
			stat.setLong(2, param.getSender_id());
			stat.setString(3, param.getMessage());
			if(param.getMessage_file()!= null) {
				stat.setBlob(4, param.getMessage_file());
			} else if(param.getMessage_file_input_stream() != null) {
				stat.setBlob(4, param.getMessage_file_input_stream());
			} else {
				 stat.setNull(4, Types.BLOB);
			}
			stat.setTimestamp(5, param.getMessage_time());
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
	public boolean updateEntityByParameter(MessageGroup param) {
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(MessageGroup param) {
		return false;
	}

	@Override
	public List<MessageGroup> getAllEntities() {
		List<MessageGroup> messages = new ArrayList<>();
		final String QUERY = "SELECT * FROM message_group";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				MessageGroup message = new MessageGroup(res.getLong(1), res.getLong(2), res.getLong(3), res.getString(4), res.getBlob(5), res.getTimestamp(6));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	@Override
	public List<MessageGroup> getAllEntitiesById(long id) {
		List<MessageGroup> messages = new ArrayList<>();
		final String QUERY = "SELECT * FROM message_group where group_chat_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				MessageGroup message = new MessageGroup(res.getLong(1), res.getLong(2), res.getLong(3), res.getString(4), res.getBlob(5), res.getTimestamp(6));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	@Override
	public MessageGroup getLastMessage(Long chat_id) {
		List<MessageGroup> list = getAllEntities();
		Stack<MessageGroup> stack = new Stack<>();
		for(MessageGroup elem : list) {
			if(elem.getChat_id() == chat_id) {
				stack.push(elem);
			}
		}
 		return stack.size() > 0 ? stack.peek() : new MessageGroup();
	}

}
