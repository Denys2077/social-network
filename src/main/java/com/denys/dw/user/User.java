package com.denys.dw.user;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Objects;
import java.util.Optional;

import com.denys.dw.user.addition.Address;
import com.denys.dw.user.addition.Birth;
import com.denys.dw.user.addition.ContactInfo;
import com.denys.dw.user.addition.SocialMedia;

public class User {
	
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private Blob photo;
	private InputStream inputStreamPhoto;
	private boolean isAdmin;
	
	private ContactInfo contactInfo;
	private Address address;
	private Birth birth;
	private SocialMedia socialMedia;
	
    private boolean isGroupAdmin;
	
	public User() {
		super();
	}
	
	public User(long id, String username, String firstName, String lastName, String password, Blob photo,
			boolean isAdmin, ContactInfo contactInfo, Address address, Birth birth, SocialMedia socialMedia) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.photo = photo;
		this.isAdmin = isAdmin;
		this.contactInfo = contactInfo;
		this.address = address;
		this.birth = birth;
		this.socialMedia = socialMedia;
	}

	public User(String username, String firstName, String lastName, String password, Blob photo, boolean isAdmin) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.photo = photo;
		this.isAdmin = isAdmin;
	}
	
	public User(long id, String username, String firstName, String lastName, String password, Blob photo, boolean isAdmin) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.photo = photo;
		this.isAdmin = isAdmin;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Blob getPhoto() {
		return photo;
	}
	
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	
	public InputStream getInputStreamPhoto() {
		return inputStreamPhoto;
	}

	public void setInputStreamPhoto(InputStream inputStreamPhoto) {
		this.inputStreamPhoto = inputStreamPhoto;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isGroupAdmin() {
		return isGroupAdmin;
	}

	public void setGroupAdmin(boolean isGroupAdmin) {
		this.isGroupAdmin = isGroupAdmin;
	}
	
	public Optional<ContactInfo> getContactInfo() {
		if(contactInfo != null) {
			return Optional.of(contactInfo);
		}
		return Optional.empty();
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Optional<Address> getAddress() {
		if(address != null) {
			return Optional.of(address);
		}
		return Optional.empty();
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Optional<Birth> getBirth() {
		if(birth != null) {
			return Optional.of(birth);
		}
		return Optional.empty();
	}

	public void setBirth(Birth birth) {
		this.birth = birth;
	}

	public Optional<SocialMedia> getSocialMedia() {
		if(socialMedia != null) {
			return Optional.of(socialMedia);
		}
		return Optional.empty();
	}

	public void setSocialMedia(SocialMedia socialMedia) {
		this.socialMedia = socialMedia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, isAdmin, lastName, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && isAdmin == other.isAdmin
				&& Objects.equals(lastName, other.lastName) && Objects.equals(username, other.username);
	}
	
	/*
	 * String methods realization
	 * */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append("User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", photo=" + photo + ", isAdmin=" + isAdmin);
		sb.append("\n Contact Info:\n");
		sb.append("[" + contactInfo + "]");
		sb.append("\n Address:\n");
		sb.append("[" + address + "]");
		sb.append("\n Birth:\n");
		sb.append("[" + birth + "]");
		sb.append("\n Social Medias:\n");
		sb.append("[" + socialMedia + "]");
		sb.append("]");
		return sb.toString();
	}
}
