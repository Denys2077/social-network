package com.denys.dw.chat.message;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Objects;

public class Message {

	private long id;
	private long chat_id;
	private long sender_id;
	private String message;
	private Blob message_file;
	private InputStream message_file_input_stream;
	private Timestamp message_time;
	
	public Message() {}

	public Message(long chat_id, long sender_id, String message, Blob message_file, Timestamp message_time) {
		super();
		this.chat_id = chat_id;
		this.sender_id = sender_id;
		this.message = message;
		this.message_file = message_file;
		this.message_time = message_time;
	} 
	
	public Message(long id, long chat_id, long sender_id, String message, Blob message_file, Timestamp message_time) {
		super();
		this.id = id;
		this.chat_id = chat_id;
		this.sender_id = sender_id;
		this.message = message;
		this.message_file = message_file;
		this.message_time = message_time;
	}

	public long getId() {
		return id;
	}

	public long getChat_id() {
		return chat_id;
	}

	public long getSender_id() {
		return sender_id;
	}

	public String getMessage() {
		return message;
	}
	
	public Blob getMessage_file() {
		return message_file;
	}
	
	public InputStream getMessage_file_input_stream() {
		return message_file_input_stream;
	}

	public Timestamp getMessage_time() {
		return message_time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}

	public void setSender_id(long sender_id) {
		this.sender_id = sender_id;
	}

	public void setMessage(String message) {
		this.message = message;
	} 
	
	public void setMessage_file(Blob message_file) {
		this.message_file = message_file;
	}

	public void setMessage_file_input_stream(InputStream message_file_input_stream) {
		this.message_file_input_stream = message_file_input_stream;
	}

	public void setMessage_time(Timestamp message_time) {
		this.message_time = message_time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chat_id, id, message, message_time, sender_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		return chat_id == other.chat_id && id == other.id && Objects.equals(message, other.message)
				&& Objects.equals(message_time, other.message_time) && sender_id == other.sender_id;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", chat_id=" + chat_id + ", sender_id=" + sender_id + ", message=" + message
				+ ", message_time=" + message_time + "]";
	}
	
}
