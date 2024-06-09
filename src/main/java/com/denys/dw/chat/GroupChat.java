package com.denys.dw.chat;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;

public class GroupChat extends Chat {

	private long creater_id;
	private String name_group;
	private Blob group_photo;
	private long connected_user_id;
	
	private InputStream inputStreamPhoto;
	private String lastMessage;
	private Timestamp time_lastMessage;
	
	public GroupChat() {}

	public GroupChat(long creater_id, String name_group, Blob group_photo, long connected_user_id) {
		super();
		this.creater_id = creater_id;
		this.name_group = name_group;
		this.group_photo = group_photo;
		this.connected_user_id = connected_user_id;
	}
	
	public GroupChat(long id, long creater_id, String name_group, Blob group_photo, long connected_user_id) {
		super(id);
		this.creater_id = creater_id;
		this.name_group = name_group;
		this.group_photo = group_photo;
		this.connected_user_id = connected_user_id;
	}

	public long getCreater_id() {
		return creater_id;
	}

	public void setCreater_id(long creater_id) {
		this.creater_id = creater_id;
	}

	public String getName_group() {
		return name_group;
	}

	public void setName_group(String name_group) {
		this.name_group = name_group;
	}

	public Blob getGroup_photo() {
		return group_photo;
	}

	public void setGroup_photo(Blob group_photo) {
		this.group_photo = group_photo;
	}

	public long getConnected_user_id() {
		return connected_user_id;
	}

	public void setConnected_user_id(long connected_user_id) {
		this.connected_user_id = connected_user_id;
	}

	public InputStream getInputStreamPhoto() {
		return inputStreamPhoto;
	}

	public void setInputStreamPhoto(InputStream inputStreamPhoto) {
		this.inputStreamPhoto = inputStreamPhoto;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public Timestamp getTime_lastMessage() {
		return time_lastMessage;
	}

	public void setTime_lastMessage(Timestamp time_lastMessage) {
		this.time_lastMessage = time_lastMessage;
	}

	@Override
	public String toString() {
		return "GroupChat [creater_id=" + creater_id + ", name_group=" + name_group + ", group_photo=" + group_photo
				+ ", connected_user_id=" + connected_user_id + "]";
	}
	
	
}
