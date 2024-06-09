package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.chat.emoji.Emoji;
import com.denys.dw.connect.ConnectorDB;

public class EmojiDAO implements AbstractDAO<Emoji> {

	@Override
	public Emoji findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public Emoji findEntityById(long id) {
		return null;
	}

	@Override
	public Emoji findEntityByObject(Emoji obj) {
		return null;
	}

	@Override
	public boolean createEntityByParameter(Emoji param) {
		final String QUERY = "INSERT INTO emoji(emoji_dec) VALUES (?)";		
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, param.getEmoji_dec());
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
	public boolean updateEntityByParameter(Emoji param) {
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(Emoji param) {
		return false;
	}

	@Override
	public List<Emoji> getAllEntities() {
		List<Emoji> emoji_faces = new ArrayList<>();
		
		final String QUERY = "SELECT * FROM emoji";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				Emoji emoji = new Emoji(res.getLong(1), res.getString(2));
				emoji_faces.add(emoji);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emoji_faces;
	}

	@Override
	public List<Emoji> getAllEntitiesById(long id) {
		return null;
	}

}
