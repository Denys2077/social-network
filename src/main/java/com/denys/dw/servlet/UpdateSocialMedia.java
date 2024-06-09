package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.SocialMediaDAO;
import com.denys.dw.user.addition.SocialMedia;

@WebServlet("/updateSocialMedia")
public class UpdateSocialMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String USER_ID = "user_id",
			                    LINKEDIN_URL = "linkedInURL",
			                    INSTAGRAM_URL = "instagramURL",
			                    FACEBOOK_URL = "facebookURL",
			                    TELEGRAM_URL = "telegramURL";
	
	private AbstractDAO<SocialMedia> mediaDAO = new SocialMediaDAO();
	
    public UpdateSocialMedia() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject obj = new JSONObject();
    	JSONArray list = new JSONArray();
    	PrintWriter out = response.getWriter();
    	
    	SocialMedia media = getSocialMedia(request);
    	
    	String msg;
    	
    	if(mediaDAO.findEntityById(media.getUser_id()) == null) {
    		System.out.println("Такой инфы нету в БД. Добавляем...");
    		if(mediaDAO.createEntityByParameter(media)) {
        		msg = "1";
            	obj.put("msg", msg);
            	list.add(obj);
            	out.println(list.toJSONString());
            	out.flush();
        	} else {
        		msg = "3";
            	obj.put("msg", msg);
            	list.add(obj);
            	out.println(list.toJSONString());
            	out.flush();
        	}
    	} else {
    		System.out.println("Така инфа есть в БД. Обновляем...");
    		if(mediaDAO.updateEntityByParameter(media)) {
        		msg = "1";
            	obj.put("msg", msg);
            	list.add(obj);
            	out.println(list.toJSONString());
            	out.flush();
        	} else {
        		msg = "3";
            	obj.put("msg", msg);
            	list.add(obj);
            	out.println(list.toJSONString());
            	out.flush();
        	}
    	}
    	
	}
	
	private SocialMedia getSocialMedia(HttpServletRequest request) {
		SocialMedia media = new SocialMedia();
		
		String linkedInUrl = request.getParameter(LINKEDIN_URL).trim();
		String instagramUrl = request.getParameter(INSTAGRAM_URL).trim();
		String facebookUrl = request.getParameter(FACEBOOK_URL).trim();
		String telegramUrl = request.getParameter(TELEGRAM_URL).trim();
		Long user_id = Long.parseLong(request.getParameter(USER_ID).trim());
		
		media.setLinkedInURL(linkedInUrl);
		media.setInstagramURL(instagramUrl);
		media.setFacebookURL(facebookUrl);
		media.setTelegramURL(telegramUrl);
		media.setUser_id(user_id);
		
		return media;
	}
}
