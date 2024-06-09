<%@page import="java.util.Base64"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.denys.dw.chat.message.Message"%>
<%@page import="com.denys.dw.dao.MessageDAO"%>
<%@page import="com.denys.dw.dao.MessageDB"%>
<%@page import="com.denys.dw.dao.UserDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.denys.dw.dao.ChatTwoUsersDAO"%>
<%@page import="com.denys.dw.chat.ChatTwoUsers"%>
<%@page import="com.denys.dw.dao.AbstractDAO"%>
<%@page import="com.denys.dw.user.User"%>
<%@page import="java.util.Map"%>
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

if (user == null) {
 response.sendRedirect("index.jsp");
 return;
}

String greeting = (String) session.getAttribute("greeting");
Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");

AbstractDAO<ChatTwoUsers> chatDAO = new ChatTwoUsersDAO();
List<ChatTwoUsers> list = chatDAO.getAllEntities();

AbstractDAO<User> userDAO = new UserDAO();
MessageDB messageDB = new MessageDAO();
%>

<%
Blob photoBlob = user.getPhoto();
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    <%@ include file="/css/styleMainPageUser.css" %>
</style>
</head>
<body>
    <header>
        <h2 class="logo-chat">Chat</h2>
        <nav class="navigation">
           <a href="#" id="home">Home</a>
           <a href="#" id="about">About</a>
           <a href="#" id="contact">Contact</a>
           <button class="btnLogin">User</button>
        </nav>
    </header>
    
    <div class="modal-window">
	    <div class="shadow-window">
	        <div class="user-window">
	            <div class="header">
	                    <% if(user.getPhoto() != null) { %>
                            <img class="photo" src="data:image/jpeg;base64, <%= photoBase64 %>">
                        <% } else { %>
                            <img class="photo" src="files/unknown_user.png" alt="img">
                        <% } %> 
	                <h3><%= user.getFirstName() %> <%= user.getLastName() %></h3>
	            </div>
	            <div class="links-user">
	                <a href="#" id="checkContacts">Check Contacts</a>
	                <a href="#" id="settings">Settings</a>
	                <a href="#" onclick="logout()">Logout</a>
	            </div>
	        </div>
	    </div>
    </div>

    <div class="wrapper-home-chat active">
         <span id="icon-close-home" class="icon-close"><ion-icon name="close"></ion-icon></span>
         <div class="input-search">
             <input type="text" placeholder="Search..." id="search-input-text" onkeyup="search_chat()" onfocus="stopLoadingChats()" onblur="startLoadingChats()">
             <span class="icon"><ion-icon name="search-outline"></ion-icon></span>
         </div>
         <div class="wrapper-list-chats">
         
         </div>
         <span class="pen"><ion-icon name="pencil-outline"></ion-icon></span>
    </div>
    
    <div class="wrapper-about">
        <span id="icon-close-about" class="icon-close-about"><ion-icon name="close"></ion-icon></span>
		<div class="form-about">
			<h2>Welcome to our application!</h2>
			<p>Thanks this app, you'll be able to communicate with your
				colleague. Simplicity of the messenger helps to keep in touch with
				your employees/workers/colleagues wherever you're.</p>
		</div>
	</div>
	
	 <div class="wrapper-contact">
        <span id="icon-close-contact" class="icon-close-contact"><ion-icon name="close"></ion-icon></span>
        <div class="form-contact">
            <h2>Contact</h2>
            <p>Developer: Ovcharenko Denys</p>
            <p>Tel: +380681234879</p>
            <p>Telegram: http://www.telegram.com/telega</p>
            <p>Instagram: http://www.instagram.com/insta</p>
        </div>
    </div>
    
    <div class="modal_wrapper">
	    <div class="shadow">
	        <div class="greeting-wrap">
	            <span class="modal_icon"><ion-icon name="heart-outline"></ion-icon></span>
	            <p><%= greeting %></p>
	        </div>
	    </div>
	</div>
	
	<div class="choose-operation-modal-wrap">
        <div class="choose-operation-shadow-wrap">
            <div class="window-wrap">
                <a href="#" id="" onclick="btn_create_group()">Create group chat</a>
                <a href="#" id="" onclick="btn_find_person()">Find person</a>
            </div>
        </div>
    </div>
    
     <div class="modal_wrapper_1">
	    <div class="shadow_1">
	        <div class="greeting-wrap_1">
	            <h3>Creating group chat</h3>
	            <input type="text" name="group_name" id="group_name" placeholder="Group Name">
	            <a href="#" onclick="create_group()">Create Group</a>
	        </div>
	    </div>
	</div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
	<!-- Відкриття модульного вікна -->
	<script>
	  const button = document.querySelector('.btnLogin');
	
  	  const modal_window = document.querySelector('.modal-window');
      const shadow_window = document.querySelector('.shadow-window');
      const user_window = document.querySelector('.user-window');  
      
      shadow_window.addEventListener('click', () => {
      	modal_window.classList.remove("active");
      	user_window.classList.remove("active");
      });
      
      button.addEventListener('click', () => {
      	modal_window.classList.add("active");
      	user_window.classList.add("active");
      });
      
      document.getElementById('checkContacts').addEventListener('click', ()=> {
    	  setTimeout(() => {
  		      window.location.href = "searchUser.jsp?windowId=<%= windowId %>";
		  }, 150);
      });
      /* Натискання на кнопу 'Settings' */
      document.getElementById('settings').addEventListener('click', ()=> {
    	  setTimeout(() => {
  		      window.location.href = "settingsPage.jsp?windowId=<%= windowId %>";
		  }, 150);
      });
      
      const home_button = document.getElementById('home');
      const about_button = document.getElementById('about');
      const contact_button = document.getElementById('contact');

      const wrapper_home = document.querySelector('.wrapper-home-chat');
      const wrapper_about = document.querySelector('.wrapper-about');
      const wrapper_contact = document.querySelector('.wrapper-contact');

      home_button.addEventListener('click', () => {
      	wrapper_home.classList.add('active');
      	deleteActive(wrapper_about, wrapper_contact);
      });
      about_button.addEventListener('click', () => {
      	wrapper_about.classList.add('active');
      	deleteActive(wrapper_home, wrapper_contact);
      });
      contact_button.addEventListener('click', () => {
      	wrapper_contact.classList.add('active');
      	deleteActive(wrapper_home, wrapper_about);
      });

      function deleteActive(wrapper_first, wrapper_second) {
      	if(wrapper_first !== null) {
      		wrapper_first.classList.remove('active');
      	}
      	if(wrapper_second !== null) {
      		wrapper_second.classList.remove('active');
      	}
      }
      const icon_close_home = document.getElementById('icon-close-home');
      icon_close_home.addEventListener('click', ()=> {
    	  wrapper_home.classList.remove('active');
      });
      const icon_close_about = document.getElementById('icon-close-about');
      icon_close_about.addEventListener('click', ()=> {
    	  wrapper_about.classList.remove('active');
      });
      const icon_close_contact = document.getElementById('icon-close-contact');
      icon_close_contact.addEventListener('click', ()=> {
    	  wrapper_contact.classList.remove('active');
      });
	</script>
	
	<script type="text/javascript">
	const modal_wrapper = document.querySelector('.modal_wrapper');
	const shadow = document.querySelector('.shadow');
    const greetingWrap = document.querySelector('.greeting-wrap');  
    
    shadow.addEventListener('click', () => {
    	modal_wrapper.classList.remove("active");
    	greetingWrap.classList.remove("active");
    });
    
    <%  if (isLoggedIn != null && isLoggedIn) { %>
            setTimeout(function() {
    	        modal_wrapper.classList.add("active");
                greetingWrap.classList.add("active");
            }, 500);
    <% } %>
    <% session.removeAttribute("isLoggedIn"); %>
	</script>
	
	<script type="text/javascript">
	    function search_user_button() {
	    	setTimeout(() => {
	    		window.location.href = "searchUser.jsp?windowId=<%= windowId %>";
			}, 100);
	    }
	</script>
	
	<script type="text/javascript">
	     document.querySelectorAll('.chat-card p').forEach(p => {
	    	 substringText(p, 25);
	     });
	     
	     function substringText(element, size) {
	    	 if (element.innerText.length > size) {
	                element.innerText = element.innerText.substring(0, size) + '...';
	        }
	     }
	</script>
	
	<script type="text/javascript">
	const choose_operation_modal_wrap = document.querySelector('.choose-operation-modal-wrap');
	const choose_operation_shadow_wrap = document.querySelector('.choose-operation-shadow-wrap');
	
	choose_operation_shadow_wrap.addEventListener('click', () => {
		choose_operation_modal_wrap.classList.remove('active');
	});
	
	document.querySelector('.pen').addEventListener('click', () => {
		choose_operation_modal_wrap.classList.add('active');
	});
	
	document.querySelector('.window-wrap').addEventListener('click', (event) => {
	    	event.stopPropagation();
	});
	
	function btn_find_person() {
		 setTimeout(() => {
 		      window.location.href = "searchUser.jsp?windowId=<%= windowId %>";
		  }, 150);
	}
	
	//
	const modal_wrapper_1 = document.querySelector('.modal_wrapper_1');
	const shadow_1 = document.querySelector('.shadow_1');
	const greeting_wrap_1 = document.querySelector('.greeting-wrap_1');
	
	shadow_1.addEventListener('click', () => {
		modal_wrapper_1.classList.remove('active');
	});
	
	greeting_wrap_1.addEventListener('click', (event) => {
    	event.stopPropagation();
    });
	
	function btn_create_group() {
		setTimeout(() => {
			choose_operation_shadow_wrap.click();
		}, 150);
		
		setTimeout(() => {
			modal_wrapper_1.classList.add('active');
		}, 300);
		
	}
	/* Функція створення групового чату */
	function create_group() {
		
		var name = $("#group_name").val();
		
		if(name.length > 1) {

			$.ajax({
				type : "POST",
				url : "creatingGroupChat",
				data : {
					name : name,
					user_id : <%= user.getId() %>,
				},
				dataType : 'JSON',
				
				success:function(data) {
					var msg = data[0].msg;
					var chat_id_db = data[0].chat_id_db;
					
					if(msg == 1) {
						var reference = reference = "chatGroup.jsp?current_user_id=<%= windowId %>" + "&chat_id_db=" + chat_id_db;
						setTimeout(() => {
			    			window.location.href = reference;
						}, 150);
					} else {
						console.log("Something went wrong...");
					}
				}, error: function(jqXhr, textStatus, errorMessage) {
		        	console.log("eror xhr : ",jqXhr)
		            console.log("textStatus error: ", textStatus);
		        	console.log("error message : ", errorMessage);
		        }
			});
			
		} else {
			console.log("Plese, enter name");
		}
	}
	</script>
	
	<script type="text/javascript">
	    document.querySelectorAll('.chat-card').forEach(card => {
	    	card.addEventListener('click', (event) => {
	    		const card_id = card.getAttribute('card-id');
	    		const chat_user = card.getAttribute('chat-user-id');
	    		const chat_id_db = card.getAttribute('chat-id-db');
	    		
	    		var reference = "chatTwoUsers.jsp?card_id=" + card_id + "&current_user_id=<%= windowId %>" + "&chat_user=" + chat_user + "&chat_id_db=" + chat_id_db;

	    		
	    		setTimeout(() => {
	    			window.location.href = reference;
				}, 150);
	    	});
	    });
	</script>
	
	<script type="text/javascript">
	function search_chat() {
		const input_line = document.getElementById('search-input-text').value.toUpperCase();
		const chats = document.querySelectorAll('.chat-card');
		
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
	
	<script type="text/javascript">
       let loadchatsInterval = setInterval(loadchats, 1000);
       
       function stopLoadingChats() {
          clearInterval(loadchatsInterval);
       }

       function startLoadingChats() {
    	   loadchatsInterval = setInterval(loadchats, 1000);
       }
       
       function loadchats() {
    	   $.post("loadchats", {user_id:<%= user.getId() %>}, function(data, status) {
       		    document.getElementsByClassName("wrapper-list-chats")[0].innerHTML = data;
       		    
       		 document.querySelectorAll('.chat-card p').forEach(p => {
    	    	 substringText(p, 25);
    	     });
    	     
    	     function substringText(element, size) {
    	    	 if (element.innerText.length > size) {
    	                element.innerText = element.innerText.substring(0, size) + '...';
    	        }
    	     }
    	     
       		 document.querySelectorAll('.chat-card').forEach(card => {
     	    	card.addEventListener('click', (event) => {
     	    		const card_id = card.getAttribute('card-id');
     	    		const chat_user = card.getAttribute('chat-user-id');
     	    		const chat_id_db = card.getAttribute('chat-id-db');
     	    		const isGroup = card.getAttribute('group');
     	    		
     	    		var reference = null;
     	    		
     	    		if(isGroup === "true") {
     	    			reference = "chatGroup.jsp?card_id=" + card_id + "&current_user_id=<%= windowId %>" + "&chat_id_db=" + chat_id_db;
     	    		} else {
     	    			reference = "chatTwoUsers.jsp?card_id=" + card_id + "&current_user_id=<%= windowId %>" + "&chat_user=" + chat_user + "&chat_id_db=" + chat_id_db;
     	    		}
     	    		
     	    		setTimeout(() => {
     	    			window.location.href = reference;
     				}, 150);
     	    	});
     	    });
    	   });
       }
       
       function logout() {
    	   
    	   $.post("logout", {window_id : '<%= windowId %>'}, function(data, status) {
    		   setTimeout(() => {
    			   window.location.href = "index.jsp";
		    	}, 100); 
    	   });
       }
	</script>
</body>
</html>