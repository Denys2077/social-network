package com.denys.dw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.denys.dw.connect.ConnectorDB;
import com.denys.dw.user.User;
import com.denys.dw.user.addition.Address;
import com.denys.dw.user.addition.Birth;
import com.denys.dw.user.addition.ContactInfo;
import com.denys.dw.user.addition.SocialMedia;

public class UserDAO implements AbstractDAO<User>{

	private AbstractDAO<ContactInfo> contactDAO = new ContactInfoDAO();
	private AbstractDAO<Address> addressDAO = new AddressDAO();
	private AbstractDAO<Birth> birthDAO = new BirthDAO();
	private AbstractDAO<SocialMedia> mediaDAO = new SocialMediaDAO();
	
	@Override
	public User findEntityByTwoParameters(String username, String password) {
		final String QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
		User user = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, username);
			stat.setString(2, password);
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				user = new User();
				user.setId(res.getInt("id"));
				user.setUsername(res.getString("username"));
				user.setFirstName(res.getString("firstName"));
				user.setLastName(res.getString("lastName"));
				user.setPassword(res.getString("password"));
				user.setPhoto(res.getBlob("photo"));
				user.setAdmin(res.getBoolean("isAdmin"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return user;
	}

	@Override
	public boolean createEntityByParameter(User user) {
		if(createUser(user)) {
			long id = getIdUser(user);
			ContactInfo contInfo = user.getContactInfo().get();
			Address address = user.getAddress().get();
			Birth birth = user.getBirth().get();
			if(id > 0) {
				if(createContactInfo(contInfo, id) && createAddress(address, id) && createBirth(birth, id)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	@Override
	public List<User> getAllEntities() {
		List<User> users = getAllUsers();
		List<ContactInfo> list_info = contactDAO.getAllEntities();
		users.stream().forEach(user -> {
			list_info.stream()
			         .filter(info -> user.getId() == info.getUserId())
			         .findFirst()
			         .ifPresent(user::setContactInfo);
		});
		
		List<Address> list_address = addressDAO.getAllEntities();
		users.stream().forEach(user -> {
			list_address.stream()
			            .filter(address -> user.getId() == address.getUser_id())
			            .findFirst()
			            .ifPresent(user::setAddress);
		});
		
		List<Birth> list_birth = birthDAO.getAllEntities();
		users.stream().forEach(user -> {
			list_birth.stream()
			          .filter(birth -> user.getId() == birth.getUser_id())
			          .findFirst()
			          .ifPresent(user::setBirth);
		});
		
		List<SocialMedia> list_media = mediaDAO.getAllEntities();
		users.stream().forEach(user -> {
			list_media.stream()
			          .filter(media -> user.getId() == media.getUser_id())
			          .findFirst()
			          .ifPresent(user::setSocialMedia);
		});
		return users;
	}
	
	@Override
	public User findEntityById(long id) {
		final String QUERY = "SELECT * FROM user WHERE id = ?";
		User user = null;
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, id);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				user = new User(res.getInt(1), 
						        res.getString(2), 
						        res.getString(3), 
						        res.getString(4), 
						        res.getString(5), 
						        res.getBlob(6), 
						        res.getBoolean(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		user.setContactInfo(contactDAO.findEntityById(id));
		user.setAddress(addressDAO.findEntityById(id));
		user.setBirth(birthDAO.findEntityById(id));
		user.setSocialMedia(mediaDAO.findEntityById(id));
		return user;
	}
	
	@Override
	public boolean updateEntityByParameter(User param) {
		final String QUERY = "UPDATE user SET username = ?, firstName = ?, lastName = ?, password = ?, photo = ? WHERE id = ?";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, param.getUsername());
			stat.setString(2, param.getFirstName());
			stat.setString(3, param.getLastName());
			stat.setString(4, param.getPassword());
			if(param.getPhoto() != null) {
				stat.setBlob(5, param.getPhoto());
			} else if(param.getInputStreamPhoto() != null) {
				stat.setBlob(5, param.getInputStreamPhoto());
			} else {
				 stat.setNull(5, Types.BLOB);
			}
			stat.setLong(6, param.getId());
			int execute = stat.executeUpdate();
			return execute > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean deleteEntityByParameter(User param) {
		final String QUERY = "DELETE FROM user WHERE id = ?";
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setLong(1, param.getId());
			return stat.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * Допоміжні методи для побудови всих даних юзера
	 * */
	private boolean createUser(User user) {
		final String USER_QUERY = "INSERT INTO user (username, firstName, lastName, password, photo, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(USER_QUERY);
			stat.setString(1, user.getUsername());
			stat.setString(2, user.getFirstName());
			stat.setString(3, user.getLastName());
			stat.setString(4, user.getPassword());
			stat.setBlob(5, user.getPhoto());
			stat.setBoolean(6, user.isAdmin());
			int rowCount = stat.executeUpdate();
			if(rowCount > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean createContactInfo(ContactInfo info, long id) {
		final String CONTACT_QUERY = "INSERT INTO contactinfo (email, telephone, position, department, user_id) VALUES (?, ?, ?, ?, ?)";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(CONTACT_QUERY);
			stat.setString(1, info.getEmail());
			stat.setString(2, info.getTelephone());
			stat.setString(3, info.getPosition());
			stat.setString(4, info.getDepartment());
			stat.setLong(5, id);
			int rowCount = stat.executeUpdate();
			if(rowCount > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean createAddress(Address address, long id) {
		final String ADDRESS_QUERY = "INSERT INTO address (country, city, street, user_id) VALUES (?, ?, ?, ?)";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(ADDRESS_QUERY);
			stat.setString(1, address.getCountry());
			stat.setString(2, address.getCity());
			stat.setString(3, address.getStreet());
			stat.setLong(4, id);
			int rowCount = stat.executeUpdate();
			if(rowCount > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean createBirth(Birth birth, long id) {
		final String BIRTH_QUERY = "INSERT INTO birth(sex, date, user_id) VALUES (?, ?, ?)";
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(BIRTH_QUERY);
			stat.setString(1, String.valueOf(birth.getSex()));
			stat.setObject(2, birth.getDate());
			stat.setLong(3, id);
			int rowCount = stat.executeUpdate();
			if(rowCount > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private long getIdUser(User user) {
		final String QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
		User expected = null;
		try (Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			stat.setString(1, user.getUsername());
			stat.setString(2, user.getPassword());
			ResultSet res = stat.executeQuery();
			while (res.next()) {
				expected = new User(res.getLong(1), 
						            res.getString(2), 
						            res.getString(3), 
						            res.getString(4), 
						            res.getString(5), 
						            res.getBlob("photo"), 
						            res.getBoolean(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return expected.getId();
	}
	
	/* Допоміжні методи для побудови списка юзерів */
	private List<User> getAllUsers() {
		final String QUERY = "SELECT * FROM user";
		List<User> users = new ArrayList<>();
		try(Connection con = ConnectorDB.getConnection()) {
			PreparedStatement stat = con.prepareStatement(QUERY);
			ResultSet res = stat.executeQuery();
			while(res.next()) {
				users.add(new User(res.getLong(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getBlob(6), res.getBoolean(7)));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return users;
	}

	@Override
	public List<User> getAllEntitiesById(long id) {
		return null;
	}

	@Override
	public User findEntityByObject(User obj) {
		return null;
	}
}
