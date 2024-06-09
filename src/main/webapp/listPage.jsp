<%@page import="java.util.Map"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="com.denys.dw.dao.UserDAO"%>
<%@page import="com.denys.dw.user.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<User> users = new UserDAO().getAllEntities();

    String windowId = (String) request.getAttribute("windowId");
    if(windowId == null) {
        windowId = request.getParameter("windowId");
    }

    Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionData");
    User user = null;

    if (sessionData != null && windowId != null) {
        user = (User) sessionData.get(windowId);
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of Users</title>
<style>
    <%@ include file="/css/styleListPage.css" %>
</style>
</head>
<body>
    <div class="back-wrapper">
        <div class="go-back">
            <span class="icon-back" id="go-back-icon"><ion-icon name="arrow-back-outline"></ion-icon></span>
        </div>
    </div>
    
  <div class="body-search-wrapper">
    <div class="form-search-box">
        <h3>Search by</h3>
        <select id="search-by">
            <option value="name">Name</option>
            <option value="department">Department</option>
            <option value="position">Position</option>
        </select>
    </div>
    <div class="form-search-box">
        <input type="text" placeholder="Search Users" id="search-input" onkeyup="searchUsers()">
        <span class="icon"><ion-icon name="search-outline"></ion-icon></span>
    </div>
    <div class="form-search-box">
        <h3>Sorting</h3>
        <select id="sorting-by">
            <option value="no">No</option>
            <option value="name-asc">By name A-Z</option>
            <option value="name-desc">By name Z-A</option>
        </select>
    </div>
</div>

    <div class="container-list-users" id="list-users">
        <% for(int i = 0; i < users.size(); i++ ) { %>
       <div class="card" id="user<%= users.get(i).getId() %>" data-user-id="<%= users.get(i).getId() %>" user-id="<%= i %>">
            <% if(users.get(i).getPhoto() == null) { %>
                 <img class="photo" src="files/unknown_user.png" alt="img">
            <% } else { %>
			<%
			Blob photoBlob = users.get(i).getPhoto();
			String photoBase64 = null;
			if (photoBlob != null) {
				try (InputStream inputStream = photoBlob.getBinaryStream()) {
					byte[] bytes = new byte[inputStream.available()];
					inputStream.read(bytes);
					photoBase64 = Base64.getEncoder().encodeToString(bytes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			%>
			<img class="photo" src="data:image/jpeg;base64, <%= photoBase64 %>">
            <% } %>
            <h2><%= users.get(i).getFirstName() %> <%= users.get(i).getLastName() %></h2>
            <p>Department: <%= users.get(i).getContactInfo().get().getDepartment() %>
            <p>Position: <%= users.get(i).getContactInfo().get().getPosition() %>
            <p><%= users.get(i).getAddress().get().getCity() %>, <%= users.get(i).getAddress().get().getCountry() %></p>
            <div class="links">
                 <% if(users.get(i).getSocialMedia().isPresent())  { %>
                     <% if (!users.get(i).getSocialMedia().get().getLinkedInURL().equals("")) { %>
                         <a href="<%= users.get(i).getSocialMedia().get().getLinkedInURL() %>"><img src="files/sign/linkedin.png"></a>
                     <% } %>
                     <% if (!users.get(i).getSocialMedia().get().getInstagramURL().equals("")) { %>
                         <a href="<%= users.get(i).getSocialMedia().get().getInstagramURL() %>"><img class="instagram-class" src="files/sign/instagram.png"></a>
                     <% } %>
                     <% if (!users.get(i).getSocialMedia().get().getFacebookURL().equals("")) { %>
                         <a href="<%= users.get(i).getSocialMedia().get().getFacebookURL() %>"><img class="border-line" src="files/sign/facebook.png"></a>
                     <% } %>
                     <% if (!users.get(i).getSocialMedia().get().getTelegramURL().equals("")) { %>
                         <a href="<%= users.get(i).getSocialMedia().get().getTelegramURL() %>"><img class="border-line" src="files/sign/telegram.png"></a>
                     <% } %>
                 <% } %>
             </div>
        </div>
        <% } %>
    </div>
    
    <div class="upd-del-user-wrap">
        <div class="shadow-wrap">
            <div class="user-wrap">
                <img id="user_photo" src="" alt="img">
                <h2 id="username"></h2>
                <p>What would you like to do?</p>
                <div class="group-btns">
                    <a href="#" class="btn" id="delete-user" onclick="delete_user()">Delete User</a> 
                    <a href="#" class="btn" id="update-user">Update User</a> 
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal_wrapper">
	    <div class="shadow">
	        <div class="success_wrap">
	            <span class="modal_icon"><ion-icon name="checkmark-outline"></ion-icon></span>
	            <p>User was successfully deleted from the system!</p>
	        </div>
	        <div class="error_wrap">
	            <span class="modal_icon"><ion-icon name="close"></ion-icon></span>
	            <p>Something went wrong during deleting current user!</p>
	        </div>
	    </div>
	</div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    
    <script type="text/javascript">
    
    const go_back = document.querySelector('.icon-back');

    go_back.addEventListener('click', () => {
    	window.location.replace('mainPage.jsp?windowId=<%= windowId %>');
    });
    

    const searchUsers = () => {
        const list_users = document.getElementById('list-users');
        const user = document.querySelectorAll('.card');
        const user_name = list_users.getElementsByTagName("h2");
        const search_line = document.getElementById("search-input").value.toUpperCase();
        const search_param = document.getElementById("search-by").value;
        
        const sorting_by = document.getElementById('sorting-by').value;
        
        for (var i = 0; i < user.length; i++) {
            let match;
            if (search_param === "name") {
                match = user[i].getElementsByTagName('h2')[0];
            } else if (search_param === "department") {
                match = user[i].getElementsByTagName('p')[0];
            } else if (search_param === "position") {
                match = user[i].getElementsByTagName('p')[1];
            }

            if (match) {
                let textValue = match.textContent || match.innerHTML;
                if (search_param !== "name") {
                    textValue = textValue.split(":")[1].trim();
                }
                if (textValue.toUpperCase().indexOf(search_line) > -1) {
                    user[i].style.display = "";
                } else {
                    user[i].style.display = "none";
                }
            }
        }
    };
    
    const sortUsers = () => {
        const sortingParam = document.getElementById("sorting-by").value;
        const usersContainer = document.getElementById("list-users");
        let usersArray = Array.from(usersContainer.getElementsByClassName("card"));

        if (sortingParam === "name-asc") {
            usersArray.sort((a, b) => {
                return a.querySelector('h2').innerText.localeCompare(b.querySelector('h2').innerText);
            });
        } else if (sortingParam === "name-desc") {
            usersArray.sort((a, b) => {
                return b.querySelector('h2').innerText.localeCompare(a.querySelector('h2').innerText);
            });
        }

        usersContainer.innerHTML = "";
        usersArray.forEach(user => {
            usersContainer.appendChild(user);
        });
    };
    document.getElementById("sorting-by").addEventListener("change", sortUsers);
    
    const upd_del_user_wrap = document.querySelector('.upd-del-user-wrap');
    const shadow_wrap = document.querySelector('.shadow-wrap');
    const user_wrap = document.querySelector('.user-wrap');
    
    shadow_wrap.addEventListener('click', () => {
   	 upd_del_user_wrap.classList.remove('active');
   	 user_wrap.classList.remove('active');
    });
    
    var referenceToNextPage = null;
    var referenceToDelete = null;
    
    document.querySelectorAll('.card').forEach(card => {
        card.addEventListener('click', (event) => {
        	if(!event.target.closest('.links')) {
        	     const userId = card.getAttribute('user-id');
        	     const bd_userId = card.getAttribute('data-user-id');
        	     const photo =  document.getElementsByClassName('photo')[userId].getAttribute('src');
                 const username = document.getElementsByTagName('h2')[userId].textContent;      
             
                 var elem = document.getElementById('username');
                 elem.innerHTML = username;
        
                 var img_elem = document.getElementById('user_photo');
                 img_elem.setAttribute('src', photo);
             
                 referenceToNextPage = "updateUserPage.jsp?userId=" + bd_userId + "&windowId=<%= windowId %>";
                 referenceToDelete = "deleteUser?userId=" + bd_userId;
                 
                 upd_del_user_wrap.classList.add('active');
           	     user_wrap.classList.add('active'); 
        	 }
        });
    });
    
    console.log(referenceToNextPage);
    
    document.querySelectorAll('.links').forEach(link => {
    	 link.addEventListener('click', () => {
    		 upd_del_user_wrap.classList.remove('active');
    	   	 user_wrap.classList.remove('active');
    	 });
    });
    
    
    const buttonUpdate = document.getElementById('update-user');
    buttonUpdate.addEventListener('click', () => {
    	upd_del_user_wrap.classList.remove('active');
        user_wrap.classList.remove('active');
        setTimeout(() => {
        	window.location.href = referenceToNextPage;
    	}, 500);
    });
    
    const modal_wrapper = document.querySelector('.modal_wrapper');
    const success = document.querySelector('.success_wrap');
    const error = document.querySelector('.error_wrap');
    const shadow = document.querySelector('.shadow');
    
    shadow.addEventListener('click', () => {
    	modal_wrapper.classList.remove("active");
    	success.classList.remove("active");
    	error.classList.remove("active");
    });
    
    var msg = null;
    function delete_user() {
    	var parts = referenceToDelete.split('=');
    	var userId = parts[1];
    	var userIdOnly = userId.split('&')[0];
    	
       $.ajax({
    	   
    	   type : "POST",
    	   url : "deleteUser",
    	   data : {
    		   'user_id' : userIdOnly,
    	   },
    	   dataType: 'JSON',
    	   
    	   success:function(data) {
    		   console.log(data[0]); 
    		   msg = data[0].msg;
    		   console.log(msg);
    		   if (msg == 1) {
    			   setTimeout(() => {
    				 modal_wrapper.classList.add("active");
    				 success.classList.add("active");
				}, 250);
    			   
                   setTimeout(() => {
   					location.reload();
   				}, 2000);
    		   } else if (msg == 3) {
    			   modal_wrapper.classList.add("active");
    			   error.classList.add("active");
    		   }
    		   
    	   }, 
           error: function(jqXhr, textStatus, errorMessage) {
        	   console.log("eror xhr : ",jqXhr)
               console.log("textStatus error: ", textStatus);
        	   console.log("error message : ", errorMessage);
           }
       });
    }


    </script>
</body>
</html>