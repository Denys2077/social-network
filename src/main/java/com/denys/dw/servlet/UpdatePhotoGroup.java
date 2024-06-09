package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2 мб
maxFileSize = 1024*1024*10,  // 10мб
maxRequestSize = 1024*1024*50)
@WebServlet("/updatePhotoGroup")
public class UpdatePhotoGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final String CHAT_ID = "chat_id", 
			                    FILE = "file";
	
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	
    public UpdatePhotoGroup() {
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
		Long chat_id = Long.parseLong(request.getParameter(CHAT_ID));
		GroupChat chat = groupDAO.findEntityById(chat_id);
		
		Part file = request.getPart(FILE);
    	InputStream inputStream = null;
    	
		if(file != null) {
			inputStream = file.getInputStream();
			chat.setInputStreamPhoto(inputStream);
		}
		
		
		if(groupDAO.updateEntityByParameter(chat)) {
			System.out.println("success update photo");
		} else {
			System.out.println("error update photo");
		}
		PrintWriter out = response.getWriter();
		
	}

}
