package com.denys.dw.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.denys.dw.chat.GroupChat;
import com.denys.dw.dao.AbstractDAO;
import com.denys.dw.dao.GroupChatDAO;
import com.denys.dw.user.User;

@WebServlet("/loadgroups")
public class LoadGroups extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
	
    public LoadGroups() {
        super();
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<GroupChat> groupChats = groupDAO.getAllEntities();
		
		Map<String, Long[]> map = new HashMap<>();

		for(GroupChat group : groupChats) {
		    if(!map.containsKey(group.getName_group())) {
		        Long[] array = new Long[2];
		        array[0] = group.getId();
		        array[1] = 1L;
		        map.put(group.getName_group(), array);
		    } else {
		        Long[] array = map.get(group.getName_group());
		        array[1] = array[1] + 1L;
		    }
		}
		
	    String data = "";
	
		for(Map.Entry<String, Long[]> obj : map.entrySet()) {
			data = data + " <div class='group-style' chat-id-db=" + obj.getValue()[0] + "><img src='files/unknown_user.png'>";
			data = data + "<h3>" + obj.getKey() + "</h3>";
			data = data + "<span>" + obj.getValue()[1] + " members</span></div>";
		}

		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();
		
		PrintWriter out = response.getWriter();
		//out.println(data);
		obj.put("data", data);
		list.add(obj);
		out.println(list.toJSONString());
		out.flush();
	}

}
