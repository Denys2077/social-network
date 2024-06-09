package com.denys.dw.user.contact;

import java.util.Objects;

public class Contact {

	private long id;
	private long current_user_id;
	private long friend_id;
	
	public Contact() {}
	
	public Contact(long current_user_id, long friend_id) {
		super();
		this.current_user_id = current_user_id;
		this.friend_id = friend_id;
	}
	
	public Contact(long id, long current_user_id, long friend_id) {
		super();
		this.id = id;
		this.current_user_id = current_user_id;
		this.friend_id = friend_id;
	}
	
	public long getId() {
		return id;
	}

	public long getCurrent_user_id() {
		return current_user_id;
	}

	public long getFriend_id() {
		return friend_id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCurrent_user_id(long current_user_id) {
		this.current_user_id = current_user_id;
	}

	public void setFriend_id(long friend_id) {
		this.friend_id = friend_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(current_user_id, friend_id, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return current_user_id == other.current_user_id && friend_id == other.friend_id && id == other.id;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", current_user_id=" + current_user_id + ", friend_id=" + friend_id + "]";
	}
	
	
	
}
