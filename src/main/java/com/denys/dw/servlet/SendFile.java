package com.denys.dw.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;

import com.denys.dw.chat.message.Message;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.MessageDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2 мб
maxFileSize = 1024*1024*10,  // 10мб
maxRequestSize = 1024*1024*50)
@WebServlet("/sendFile")
public class SendFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String CHAT_ID = "chat_id_db",
			                    SENDER_ID = "sender_id",
			                    MESSAGE = "message",
			                    FILE_NAME = "file";

	private AbstractDAO<Message> messageDAO = new MessageDAO();
	
    public SendFile() {
        super();
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Message message = getMessage(request);
		PrintWriter out = response.getWriter();
		if(message != null) {
			messageDAO.createEntityByParameter(message);
			out.println("");
		}
	}

	private Message getMessage(HttpServletRequest request) throws IOException, ServletException {
		Message inst = null;
		String chat_id_db = request.getParameter(CHAT_ID);
		String sender_id = request.getParameter(SENDER_ID);
		String message = request.getParameter(MESSAGE);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Long chat_id = Long.parseLong(chat_id_db);
		
		inst = new Message(chat_id, Long.parseLong(sender_id), message,null, timestamp);
		
		Part file = request.getPart(FILE_NAME);
    	InputStream inputStream = null;
    	
		if(file != null) {
			inputStream = file.getInputStream();
			inst.setMessage_file_input_stream(inputStream);
		}
		
		return inst;
	}
}
