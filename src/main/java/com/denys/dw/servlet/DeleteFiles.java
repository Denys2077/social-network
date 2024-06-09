package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.denys.dw.logic.FileLogic;

@WebServlet("/deleteFiles")
public class DeleteFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private static final String DIRECTORY_PATH = "D:\\Java_Advanced\\DiplomaWork\\src\\main\\webapp\\files\\temporary";
    
    public DeleteFiles() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}


	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = FileLogic.deleteFile(DIRECTORY_PATH);
        response.setContentType("application/json");
        response.getWriter().write("{\"success\": " + success + "}");
	}

}
