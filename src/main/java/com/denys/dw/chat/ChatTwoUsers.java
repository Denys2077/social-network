package com.denys.dw.chat;

import java.sql.Timestamp;

public class ChatTwoUsers extends Chat {

	private long first_user_id;
	private long second_user_id;
	private String last_message;
	private Timestamp time_last_message;
	
	public ChatTwoUsers() {
		
	}
	
	public ChatTwoUsers(long id, long first_user_id, long second_user_id) {
		super(id);
		this.first_user_id = first_user_id;
		this.second_user_id = second_user_id;
	}

	public long getFirst_user_id() {
		return first_user_id;
	}

	public long getSecond_user_id() {
		return second_user_id;
	}

	public String getLast_message() {
		return last_message;
	}
	
	public void setFirst_user_id(long first_user_id) {
		this.first_user_id = first_user_id;
	}

	public void setSecond_user_id(long second_user_id) {
		this.second_user_id = second_user_id;
	}

	public void setLast_message(String last_message) {
		this.last_message = last_message;
	}

	public Timestamp getTime_last_message() {
		return time_last_message;
	}

	public void setTime_last_message(Timestamp time_last_message) {
		this.time_last_message = time_last_message;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Chat for two users [id=")
		  .append(super.getId()).append(", first user id=")
		  .append(this.getFirst_user_id()).append(", second user id=")
		  .append(this.getSecond_user_id()).append("]");
		return sb.toString();
	}
	
}
