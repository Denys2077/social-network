package com.denys.dw.dao;

import com.denys.dw.chat.message.Message;

public interface MessageDB {

	Message getLastMessage(Long chat_id);
}
