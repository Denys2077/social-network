package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.connect.ConnectorDB;
import com.denys.dw.user.addition.SocialMedia;

public class SocialMediaDAO implements AbstractDAO<SocialMedia>{

	@Override
	public SocialMedia findEntityByTwoParameters(String paramOne, String paramTwo) {
		return null;
	}

	@Override
	public SocialMedia findEntityById(long id) {
		final String QUERY = "SELECT * FROM socialmedia WHERE user_id = ?";
		SocialMedia media = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				media = new SocialMedia();
				media.setLinkedInURL(res.getString("linkedInURL"));
				media.setInstagramURL(res.getString("instagramURL"));
				media.setFacebookURL(res.getString("facebookURL"));
				media.setTelegramURL(res.getString("telegramURL"));
				media.setUser_id(res.getLong("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return media;
	}

	@Override
	public SocialMedia findEntityByObject(SocialMedia obj) {
		return null;
	}

	@Override
	public boolean createEntityByParameter(SocialMedia param) {
		final String QUERY = "INSERT INTO socialmedia (linkedInURL, instagramURL, facebookURL, telegramURL, user_id) VALUES (?, ?, ?, ?, ?)";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, param.getLinkedInURL());
			stat.setString(2, param.getInstagramURL());
			stat.setString(3, param.getFacebookURL());
			stat.setString(4, param.getTelegramURL());
			stat.setLong(5, param.getUser_id());
			
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
	public boolean updateEntityByParameter(SocialMedia param) {
		final String QUERY = "UPDATE socialmedia SET linkedInURL = ?, instagramURL = ?, facebookURL = ?, telegramURL = ? WHERE user_id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, param.getLinkedInURL());
			stat.setString(2, param.getInstagramURL());
			stat.setString(3, param.getFacebookURL());
			stat.setString(4, param.getTelegramURL());
			stat.setLong(5, param.getUser_id());
			
			int execute = stat.executeUpdate();
			return execute > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteEntityByParameter(SocialMedia param) {
		return false;
	}

	@Override
	public List<SocialMedia> getAllEntities() {
		List<SocialMedia> list = new ArrayList<>();
		final String QUERY = "SELECT * FROM socialmedia";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			SocialMedia media;
			while(res.next()) {
				media = new SocialMedia();
				media.setLinkedInURL(res.getString(2));
				media.setInstagramURL(res.getString(3));
				media.setFacebookURL(res.getString(4));
				media.setTelegramURL(res.getString(5));
				media.setUser_id(res.getLong(6));
				list.add(media);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<SocialMedia> getAllEntitiesById(long id) {
		return null;
	}

}
