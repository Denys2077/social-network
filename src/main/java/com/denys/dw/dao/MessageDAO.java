package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.denys.dw.chat.message.Message;
import com.denys.dw.connect.ConnectorDB;

public class MessageDAO implements AbstractDAO<Message>, MessageDB{

	@Override
	public Message findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public Message findEntityById(long id) {
		return null;
	}
	
	@Override
	public boolean createEntityByParameter(Message param) {
		final String QUERY = "INSERT INTO messageTwousers (chat_id, sender_id, message, message_file, message_time) VALUES (?, ?, ?, ?, ?)";
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
	public boolean updateEntityByParameter(Message param) {
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(Message param) {
		return false;
	}

	@Override
	public List<Message> getAllEntities() {
		List<Message> messages = new ArrayList<>();
		final String QUERY = "SELECT * FROM messageTwousers";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				Message message = new Message(res.getLong(1), res.getLong(2), res.getLong(3), res.getString(4), res.getBlob(5), res.getTimestamp(6));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	@Override
	public List<Message> getAllEntitiesById(long id) {
		List<Message> list = new ArrayList<>();

		final String QUERY = "SELECT * FROM messageTwousers WHERE chat_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				Message message = new Message(
						res.getLong("id"),
						res.getLong("chat_id"),
						res.getLong("sender_id"),
						res.getString("message"),
						res.getBlob("message_file"),
						res.getTimestamp("message_time")
						);
				list.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Message getLastMessage(Long chat_id) {
		List<Message> list = getAllEntities();
		Stack<Message> stack = new Stack<>();
		for(Message elem : list) {
			if(elem.getChat_id() == chat_id) {
				stack.push(elem);
			}
		}
 		return stack.size() > 0 ? stack.peek() : new Message();
	}

	@Override
	public Message findEntityByObject(Message obj) {
		return null;
	}

}
