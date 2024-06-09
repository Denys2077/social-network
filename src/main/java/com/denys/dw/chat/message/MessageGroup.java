package com.denys.dw.chat.message;

import java.sql.Blob;
import java.sql.Timestamp;

public class MessageGroup extends Message {

	public MessageGroup() {}

	public MessageGroup(long group_chat_id, long sender_id, String message, Blob message_file, Timestamp message_time) {
		super(group_chat_id, sender_id, message, message_file, message_time);
	} 
	
	public MessageGroup(long id, long group_chat_id, long sender_id, String message, Blob message_file, Timestamp message_time) {
		super(id, group_chat_id, sender_id, message, message_file, message_time);
	} 
}
