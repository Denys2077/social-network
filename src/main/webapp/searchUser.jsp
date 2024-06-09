<%@page import="java.util.HashMap"%>
<%@page import="com.denys.dw.dao.AbstractDAO"%>
<%@page import="com.denys.dw.dao.GroupChatDAO"%>
<%@page import="com.denys.dw.chat.GroupChat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.denys.dw.user.User"%>
<%
String windowId = (String) request.getAttribute("windowId");
if(windowId == null) {
    windowId = request.getParameter("windowId");
}

Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionData");
User user = null;

if (sessionData != null && windowId != null) {
    user = (User) sessionData.get(windowId);
}

AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
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
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    <%@ include file="/css/styleSearchUser.css" %>
</style>
</head>
<body>
  <div class="back-wrapper">
        <div class="go-back">
            <span class="icon-back" id="go-back-icon"><ion-icon name="arrow-back-outline"></ion-icon></span>
        </div>
    </div>
    
    <div class="wrapper">
         <div class="page-buttons">
             <a href="#" id="contacts-link">Contacts</a>
             <a href="#" id="users-link">Users</a>
        </div>
        <div class="form-box contacts" id="list-contacts">
            <h2>Contacts</h2>
            <div class="search-menu">
                  <h3>Search By</h3>
                  <div class="input-box">
                      <select id="search-contacts-by" onfocus="stopLoadingContacts()" onblur="startLoadingContacts()">
                          <option value="name">Name</option>
                          <option value="department">Department</option>
                          <option value="position">Position</option>
                          <option value="telephone">Telephone</option>
                      </select>
                  </div>
                  <div class="input-box">
                      <input type="text" placeholder="" id="search-input-contacts" onkeyup="search_contacts()" onfocus="stopLoadingContacts()" onblur="startLoadingContacts()">
                      <span class="icon"><ion-icon name="search-outline"></ion-icon></span>
                  </div>
            </div>
            <div class="contact-list" id="contact-list">
            <!--
                <div class="user-info-contact">
                     <img src="files/user2.jpg" alt="img">
                      <div class="user-message" id="user-info">
                          <h3>Denys Ovcharenko</h3>
                          <h4>Department: IT</h4>
                          <h4>Position: QA Testing</h4>
                          <h4>Telephone: 0681251566</h4>
                      </div>
                </div>
                 -->
                
            </div>
        </div>
        
         <div class="form-box users">
            <h2>Users</h2>
            <div class="search-menu">
                  <h3>Search By</h3>
                  <div class="input-box">
                      <select id="search-users-by"  onfocus="stopLoadingUsers()" onblur="startLoadingUsers()">
                          <option value="name">Name</option>
                          <option value="department">Department</option>
                          <option value="position">Position</option>
                          <option value="telephone">Telephone</option>
                      </select>
                  </div>
                  <div class="input-box">
                      <input type="text" placeholder="" id="search-input-users" onkeyup="search_users()" onfocus="stopLoadingUsers()" onblur="startLoadingUsers()">
                      <span class="icon"><ion-icon name="search-outline"></ion-icon></span>
                  </div>
            </div>
            <div class="contact-list">
<!--
                 <div class="user-info-users">
                     <img src="files/unknown_user.png" alt="img">
                      <div class="user-message">
                          <h3>Dmitriy Vasilenko</h3>
                          <h4>Department: IT</h4>
                          <h4>Position: QA Testing</h4>
                          <h4>Telephone: 0953325998</h4>
                      </div>
                </div>
               -->
            </div>
        </div>
    </div>
    
    <div class="card">
        <div class="shadow-card">
            <div class="user-wrap">
            <span class="icon-close"><ion-icon name="close"></ion-icon></span>
                <img id="user_photo" src="" alt="img">
                
                <h2 id="first_name"></h2>
                <h2 id="last_name"></h2>
                
                <div class="btns-act">
                    <button onclick="deleteUserFromContact()">Remove User From Contacts</button>
                    <button onclick="createChat()">Create Chat</button>
                    <button onclick="addToGroupChat()">Add to Group chat</button>
                </div>
            </div>
        </div>
    </div>
    
     <div class="card-dont-contact">
        <div class="shadow-card-contact">
            <div class="contact-wrap">
            <span class="icon-close"><ion-icon name="close"></ion-icon></span>
                <img id="contact_photo" src="" alt="img">
                
                <h2 id="first_name_contact"></h2>
                <h2 id="last_name_contact"></h2>
                
                <div class="btns-act">
                    <button onclick="addUserToContacts()">Add User To Contacts</button>
                    <button onclick="createChat()">Create Chat</button>
                    <button onclick="addToGroupChat()">Add to Group chat</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="group-wrap-out">
        <div class="group-wrap-shadow">
            <div class="group-wrap-window">
                <h2>Groups</h2>
                <div class="menu-search">
                    <input type="text" id="search-group-text" onkeyup="search_group()">
                </div>
                 <div class="group-list">
                 <% for(Map.Entry<String, Long[]> obj : map.entrySet()) { %>
                     <div class="group-style" chat-id-db="<%= obj.getValue()[0]%>">
                         <img src="files/unknown_user.png">
                         <h3><%= obj.getKey() %></h3>
                         <span><%= obj.getValue()[1] %> members</span>
                     </div>
                 <% } %>
                 </div>
            </div>
        </div>
    </div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    
    <script type="text/javascript">
    
    const icon_back = document.querySelector('.icon-back');
    
    icon_back.addEventListener('click', () => {
    	setTimeout(() => {
    		window.location.href = "mainPageUser.jsp?windowId=<%= windowId %>";
		}, 100);
    });
    </script>
    
    <script type="text/javascript">
    /**
     * 
     */
    const wrapper = document.querySelector('.wrapper');
    const contacts_link = document.getElementById('contacts-link');
    const users_link = document.getElementById('users-link');

    users_link.addEventListener('click', ()=> {
    	wrapper.classList.add('active');
    	contacts_link.classList.remove('active');
    	users_link.classList.add('active');
    });

    contacts_link.addEventListener('click', ()=> {
    	wrapper.classList.remove('active');
    	users_link.classList.remove('active');
    	contacts_link.classList.add('active');
    });
    </script>
    
    <script type="text/javascript">
    function search_contacts() {
    	const users = document.querySelectorAll('.user-info-contact');
    	const search_line = document.getElementById('search-input-contacts').value.toUpperCase();
    	const search_param = document.getElementById('search-contacts-by').value;

    	for (var i = 0; i < users.length; i++) {
    		var match;

    		if (search_param === "name") {
    			match = users[i].getElementsByTagName('h3')[0];
    		} else if (search_param === "department") {
    			match = users[i].getElementsByTagName('h4')[0];
    		} else if (search_param === "position") {
    			match = users[i].getElementsByTagName('h4')[1];
    		} else {
    			match = users[i].getElementsByTagName('h4')[2];
    		}

    		if (match) {
    			let textValue = match.textContent || match.innerHTML;
    			if (search_param !== "name") {
    				textValue = textValue.split(":")[1].trim();
    			}
    			if (textValue.toUpperCase().indexOf(search_line) > -1) {
    				users[i].style.display = "";
    			} else {
    				users[i].style.display = "none";
    			}
    		}
    	}
    }
    
    function search_users() {
    	const users = document.querySelectorAll('.user-info-users');
	const search_line = document.getElementById('search-input-users').value.toUpperCase();
	const search_param = document.getElementById('search-users-by').value;

	for (var i = 0; i < users.length; i++) {
		var match;

		if (search_param === "name") {
			match = users[i].getElementsByTagName('h3')[0];
		} else if (search_param === "department") {
			match = users[i].getElementsByTagName('h4')[0];
		} else if (search_param === "position") {
			match = users[i].getElementsByTagName('h4')[1];
		} else {
			match = users[i].getElementsByTagName('h4')[2];
		}

		if (match) {
			let textValue = match.textContent || match.innerHTML;
			if (search_param !== "name") {
				textValue = textValue.split(":")[1].trim();
			}
			if (textValue.toUpperCase().indexOf(search_line) > -1) {
				users[i].style.display = "";
			} else {
				users[i].style.display = "none";
			}
		}
	}
}
    </script>
    <script type="text/javascript">
       let loadContactsInterval = setInterval(loadcontacts, 1000);
        
       function stopLoadingContacts() {
           clearInterval(loadContactsInterval);
       }

       function startLoadingContacts() {
           loadContactsInterval = setInterval(loadcontacts, 1000);
       }
       
       const card = document.querySelector('.card');
       const shadow = document.querySelector('.shadow-card');
       const user_wrap = document.querySelector('.user-wrap');
       
       shadow.addEventListener('click', () => {
    	   card.classList.remove('active');
      	   user_wrap.classList.remove('active');
      	   startLoadingContacts();
       });
       
       var deleteReference = null;
       var referenceCreateChat = null;
        function loadcontacts() {
        	$.post("loadcontacts",{user_id:<%= user.getId()%>}, function(data, status){
        		document.getElementsByClassName("contact-list")[0].innerHTML = data;
        		
        		  document.querySelectorAll('.user-info-contact').forEach(contact => {
        		     	contact.addEventListener('click', (event) => {
        		     		stopLoadingContacts();
        		     		
        		     		 const card_id = contact.getAttribute('id');
        	        	     const bd_userId = contact.getAttribute('data-user-id');
        	        	     const photo =  document.getElementsByTagName('img')[card_id].getAttribute('src');
        	                 const fullname = document.getElementsByClassName('fullname')[card_id].textContent;      
        	                 
        	                 const [firstNameElement, lastNameElement] = fullname.split(' ');
        	                
        	                 var user_photo = document.getElementById('user_photo');
        	                 user_photo.setAttribute('src', photo);
        	                 
        	                 var firstname = document.getElementById('first_name');
        	                 firstname.innerHTML = firstNameElement;
        	                 
        	                 var lastname = document.getElementById('last_name');
        	                 lastname.innerHTML = lastNameElement;
        	                
        	                 deleteReference = "friend_id=" + bd_userId + ",current_id=<%= user.getId() %>";
        	                 referenceCreateChat = "friend_id=" + bd_userId + ",current_id=<%= user.getId() %>";
        		     		card.classList.add('active');
        		           	user_wrap.classList.add('active'); 
        		           	
        		           	     
        		     	});
        		     });
        	});
        }
        
        function deleteUserFromContact() {
        	const parts = deleteReference.split(',');

        	const friend_id_value = parts[0].split('=')[1];
        	const current_id_value = parts[1].split('=')[1];
        	
        	$.post("deleteUserFromContact",{current_id:current_id_value, friend_id:friend_id_value}, function(data, status){
        		console.log("user with id: " + friend_id_value + " was removed from your contacts");
        	});
        }
        
        
       let loadUsersInterval = setInterval(loadusers, 1000);
       
       function stopLoadingUsers() {
          clearInterval(loadUsersInterval);
       }

       function startLoadingUsers() {
    	   loadUsersInterval = setInterval(loadusers, 1000);
       }
       
       const card_dont_contact = document.querySelector('.card-dont-contact');
       const shadow_card_contact = document.querySelector('.shadow-card-contact');
       const contact_wrap = document.querySelector('.contact-wrap');
       
       shadow_card_contact.addEventListener('click', () => {
    	   card_dont_contact.classList.remove('active');
    	   contact_wrap.classList.remove('active');
      	   startLoadingUsers();
       });
       
       var referenceAddUser = null;
       
        function loadusers() {
        	$.post("loadusers", {user_id:<%= user.getId() %>}, function(data, status) {
        		document.getElementsByClassName("contact-list")[1].innerHTML = data;
        		
        		 document.querySelectorAll('.user-info-users').forEach(user => {
        			 user.addEventListener('click', (event) => {
       		     		 stopLoadingUsers();
       		     		
       		     		 const card_id = user.getAttribute('id');
       	        	     const bd_userId = user.getAttribute('data-user-id');
       	        	     const photo =  document.getElementsByTagName('img')[card_id].getAttribute('src');
       	                 const fullname = document.getElementsByClassName('contact-full-name')[card_id].textContent;      
       	                 
       	                 const [firstNameElement, lastNameElement] = fullname.split(' ');
       	                
       	                 var contact_photo = document.getElementById('contact_photo');
       	                 contact_photo.setAttribute('src', photo);
       	                 
       	                 var first_name_contact = document.getElementById('first_name_contact');
       	                 first_name_contact.innerHTML = firstNameElement;
       	                 
       	                 var last_name_contact = document.getElementById('last_name_contact');
       	                 last_name_contact.innerHTML = lastNameElement;
       	                
       	                 referenceAddUser = "friend_id=" + bd_userId + ",current_id=<%= user.getId() %>";
       	                 referenceCreateChat = "friend_id=" + bd_userId + ",current_id=<%= user.getId() %>";
       	                 
       		     		card_dont_contact.classList.add('active');
       		           	contact_wrap.classList.add('active');   
       		     	});
       		     });
        	});
        }
        
        function addUserToContacts() {
        	const parts = referenceAddUser.split(',');

        	const friend_id_value = parts[0].split('=')[1];
        	const current_id_value = parts[1].split('=')[1];
        	
        	$.post("addUserToContacts",{current_id:current_id_value, friend_id:friend_id_value}, function(data, status){
        		console.log("user with id: " + friend_id_value + " was added to your contacts");
        	});
        }
    </script>
    
    <script type="text/javascript">
    function createChat() {
    	const parts = referenceCreateChat.split(',');

    	const friend_id_value = parts[0].split('=')[1];
    	const current_id_value = parts[1].split('=')[1];

    	$.ajax({
    		
    		type : 'POST',
    		url : 'createChat',
    		data : {
    			'current_id_value' : current_id_value,
    			'friend_id_value' : friend_id_value,
    			'windowId' :'<%= windowId %>',
    		},
    		dataType : 'JSON',
    		
    		success:function(data) {
    			
    			var chat_id_db = data[0].chat_id_db;
    			var chat_user = data[0].chat_user;
    			
    			var reference = "chatTwoUsers.jsp?&current_user_id=<%= windowId %>" + "&chat_user=" + chat_user + "&chat_id_db=" + chat_id_db;
    		
    			 setTimeout(() => {
					 window.location.href = reference;
				}, 500);
    		}
    	});
    }
    
    const group_wrap_out = document.querySelector('.group-wrap-out');
    const group_wrap_shadow = document.querySelector('.group-wrap-shadow');
    const group_wrap_window = document.querySelector('.group-wrap-window');
    
    group_wrap_shadow.addEventListener('click', ()=> {
    	group_wrap_out.classList.remove('active');
    });
    
    group_wrap_window.addEventListener('click', (event) => {
    	event.stopPropagation();
    });
    
    function addToGroupChat() {
    	setTimeout(() => {
    		group_wrap_out.classList.add('active');
		}, 100);
    	
    }

    document.querySelectorAll('.group-style').forEach(card => {
    	card.addEventListener('click', ()=> {
        	const parts = referenceCreateChat.split(',');
        	
        	const friend_id_value = parts[0].split('=')[1];
    		var chat_id = card.getAttribute('chat-id-db');
    		
    		//console.log("add user id=" + friend_id_value + " to chat with id=" + chat_id_db);
    		$.ajax({
        		
        		type : 'POST',
        		url : 'addUserToGroupChat',
        		data : {
        			'chat_id_db' : chat_id,
        			'friend_id_value' : friend_id_value,
        		},
        		dataType : 'JSON',
        		
        		success:function(data) {
        			
        			var chat_id_db = data[0].chat_id_db;

        		    var reference = reference = "chatGroup.jsp?current_user_id=<%= windowId %>" + "&chat_id_db=" + chat_id_db;
        			
        		    setTimeout(() => {
    					 window.location.href = reference;
    				}, 500);
        		}
        	});
    	});
    });
    
    function search_group() {
    	const input_line = document.getElementById('search-group-text').value.toUpperCase();
		const chats = document.querySelectorAll('.group-style');
		
		for(var i = 0; i < chats.length; i++) {
			var match = chats[i].getElementsByTagName('h3')[0];
			
			if (match) {
				let textValue = match.textContent || match.innerHTML;
		
				if (textValue.toUpperCase().indexOf(input_line) > -1) {
					chats[i].style.display = "";
				} else {
					chats[i].style.display = "none";
				}
			}
		}
    }
    </script>
</body>
</html>