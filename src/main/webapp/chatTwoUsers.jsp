<%@page import="com.denys.dw.dao.EmojiDAO"%>
<%@page import="com.denys.dw.chat.emoji.Emoji"%>
<%@page import="java.util.List"%>
<%@page import="com.denys.dw.dao.ContactDAO"%>
<%@page import="com.denys.dw.user.contact.Contact"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="com.denys.dw.dao.UserDAO"%>
<%@page import="com.denys.dw.dao.AbstractDAO"%>
<%@page import="com.denys.dw.user.User"%>
<%@page import="java.util.Map"%>
<%
String windowId = (String) request.getAttribute("windowId");
if(windowId == null) {
    windowId = request.getParameter("current_user_id");
}

Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionData");
User user = null;

if (sessionData != null && windowId != null) {
    user = (User) sessionData.get(windowId);
}

String chat_id = request.getParameter("chat_id_db");
String chat_user = request.getParameter("chat_user");
if(chat_id == null) {
	 chat_id = (String) request.getAttribute("chat_id_db");
}
if(chat_user == null) {
	chat_user = (String) request.getAttribute("chat_user");
}
AbstractDAO<User> userdao = new UserDAO();

User second_user = userdao.findEntityById(Long.parseLong(chat_user));
Blob photoBlob = second_user.getPhoto();
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

AbstractDAO<Contact> contactDAO = new ContactDAO();
List<Contact> contacts = contactDAO.getAllEntitiesById(user.getId());

AbstractDAO<Emoji> emojiDAO = new EmojiDAO();
List<Emoji> emoji_faces = emojiDAO.getAllEntities();
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    <%@ include file="/css/styleChatTwoUsers.css" %>
</style>
</head>
<body>
    <header id="header-user">
        <h2 class="logo-chat">Chat</h2>
        <% if(second_user.getPhoto() != null) { %>
             <img src="data:image/jpeg;base64, <%= photoBase64 %>" alt="img">
        <% } else { %>
            <img src="files/unknown_user.png" alt="img">
        <% } %>
         <div class="second-user">
           
         </div>
    </header>
    
      <div class="back-wrapper">
        <div class="go-back">
            <span class="icon-back" id="go-back-icon"><ion-icon name="arrow-back-outline"></ion-icon></span>
        </div>
    </div>

    <div class="container-messages">
       <!-- 
       <div class="my-messages">
           <a href="files/admins.txt" onclick="downloadFile()" id="file1"><img class="file-sign" src="files/sign/file.png"></a>
           <span>20:33</span>
       </div>
       
       <div class="other-user-messages">
           <img src="files/background.png" id="photo1" onclick="look_photo()">
           <span>20:34</span>
       </div>
      -->
    </div>
       <div class="wrapper-borders" id="wrapper-borders">
        <div class="wrapper-smile-out">
            <div class="smile-window">
                <% for(Emoji emoji : emoji_faces) { %>
                    <p id="<%= emoji.getId()%>" onclick="emoji_click('<%= emoji.getEmoji_dec()%>')"><%= emoji.getEmoji_dec() %></p>
                <% } %>
            </div> 
        </div>
    </div>
    
    <div class="send-menu-wrap">
        <form class="form" action="sendFile" method="POST" enctype="multipart/form-data" id="file-form">
            <label for="input-file" class="add-file"><ion-icon name="document-attach-outline"></ion-icon></label>
            <input type="file" id="input-file" name="file" accept="image/png, .txt" class="hidden-input">
            <input type="hidden" name="chat_id_db" value="<%= chat_id%>">
            <input type="hidden" name="sender_id" value="<%= user.getId() %>">
            <input type="hidden" name="message" value=" ">
        </form>
        <input id="message"type="text" name="message" placeholder="Message">
        <span class="add-smile" onclick="add_emoji()"><ion-icon name="happy-outline"></ion-icon></span>
        <span id="send" class="send-message"><ion-icon name="send-outline"></ion-icon></span>
    </div>
    
    <div class="user-info-wrap">
        <div class="shadow">
            <div class="user-wrap">
                 <span class="icon-close"><ion-icon name="close"></ion-icon></span>
                 <div class="wrap-photo-user">
                      <% if(second_user.getPhoto() != null) { %>
                           <img src="data:image/jpeg;base64, <%= photoBase64 %>" alt="img">
                      <% } else { %>
                          <img src="files/unknown_user.png" alt="img">
                      <% } %>
                 </div>
                 <div class="wrap-account-user">
 
                 </div>
                 <div class="wrap-contact-info-user">
                    <span class="icon"><ion-icon name="bulb-outline"></ion-icon></span>
                    <h3>Contact</h3>
                    <h4>Username: <%= second_user.getUsername() %></h4>
                    <h4>Telephone: <%=  second_user.getContactInfo().get().getTelephone()%></h4>
                    <hr />
                    <span class="icon"><ion-icon name="globe-outline"></ion-icon></span>
                    <h3>Social Media</h3>
                    <div class="links">
                        <% if(second_user.getSocialMedia().isPresent())  { %>
                            <% if (!second_user.getSocialMedia().get().getLinkedInURL().equals("")) { %>
                                <a href="<%= second_user.getSocialMedia().get().getLinkedInURL()%>"><img src="files/sign/linkedin.png"></a>
                            <% } %>
                            <% if (!second_user.getSocialMedia().get().getInstagramURL().equals("")) { %>
                                <a href="<%= second_user.getSocialMedia().get().getInstagramURL() %>"><img class="instagram-class" src="files/sign/instagram.png"></a>
                            <% } %>
                            <% if (!second_user.getSocialMedia().get().getFacebookURL().equals("")) { %>
                                <a href="<%= second_user.getSocialMedia().get().getFacebookURL() %>"><img class="border-line" src="files/sign/facebook.png"></a>
                            <% } %>
                            <% if (!second_user.getSocialMedia().get().getTelegramURL().equals("")) { %>
                                <a href="<%= second_user.getSocialMedia().get().getTelegramURL() %>"><img class="border-line" src="files/sign/telegram.png"></a>
                            <% } %>
                        <% } %>
                    </div>    
                    <hr />
                    <span class="icon"><ion-icon name="settings-outline"></ion-icon></span>
                    <h3>Settings</h3>
                    <div class="friend-status">
                     
                     </div>
                 </div>
            </div>
        </div>
    </div>
    
    <div class="modal-wrapper" id="modal-wrapper">
        <div class="shadow" id="shadow">
           <div class="blob-window">
               <h2 id="modal-title"></h2>
               <span class="icon-close" id="close-icon"><ion-icon name="close"></ion-icon></span>
               <img src="" alt="img" id="img-file">
               <div id="txt-preview" class="txt-preview" style="display: none;">
                   <span><ion-icon name="document-outline"></ion-icon></span>
                   <p id="txt-name"></p>
               </div>
               <button id="input-send" class="input-send" type="button" name="button" onclick="sendData()">Send File</button>
           </div>
        </div>
    </div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    
    <script type="text/javascript">
    function look_photo() {
    	   var photoUrl = document.getElementById('photo1').src;
    	    
    	    var downloadLink = document.createElement('a');// назва файлу
    	    downloadLink.href = photoUrl;
    	    downloadLink.download = 'background.png';
    	    downloadLink.target = '_blank';
    	    
    	    document.body.appendChild(downloadLink);
    	    
    	    downloadLink.click();
    	    
    	    document.body.removeChild(downloadLink);
    }
    
    function downloadFile() {
    	  var fileUrl = document.getElementById('file1').href;
    	    
    	   var downloadLink = document.createElement('a');
    	    downloadLink.href = fileUrl;
    	    downloadLink.download = 'azaza.txt'; // назва файлу
    	    downloadLink.target = '_blank';
    	    
    	    document.body.appendChild(downloadLink);
    	    
    	    downloadLink.click();
    	    
    	    document.body.removeChild(downloadLink);
    }
    </script>
    
    <script type="text/javascript">
    const wrapper = document.querySelector('.user-info-wrap');
    const shadow = document.querySelector('.shadow');
    const user_wrap = document.querySelector('.user-wrap');

    shadow.addEventListener('click', () => {
    	wrapper.classList.remove('active');
    	user_wrap.classList.remove('active');
    });

    const header_user = document.getElementById('header-user');
    header_user.addEventListener('click', () => {
    	wrapper.classList.add('active');
    	user_wrap.classList.add('active');
    });
    
    const close_button = document.querySelector('.icon-close');
    close_button.addEventListener('click', () => {
    	wrapper.classList.remove('active');
    	user_wrap.classList.remove('active');
    });
    
    user_wrap.addEventListener('click', (event) => {
    	event.stopPropagation();
    });
    </script>
    
    <script type="text/javascript">
        const icon_back = document.querySelector('.icon-back');
        
        icon_back.addEventListener('click', () => {
        	setTimeout(() => {
        		window.location.href = "mainPageUser.jsp?windowId=<%= windowId %>";
			}, 100);
        }); 
       
    </script>
    
    <script type="text/javascript">
    
    setInterval(loadmessage, 500);
    
    function loadmessage() {
    	
    	$.post("loadmessage",{chat_id_db:<%= chat_id %>, sender_id:<%= user.getId() %>}, function(data, status){
    		document.getElementsByClassName("container-messages")[0].innerHTML=data;
    		
    	});
    }
    
    $(document).ready(function() {
    	
    	var inputbox = document.getElementById("message");
    	inputbox.addEventListener('keyup', function(event){
    		if(event.keyCode==13){
    			$("#send").click();
    		}
    	});
    	
    	$("#send").click(function(){
    		 var message = $("#message").val();

    		 $.post("sendmessage",{chat_id_db:<%= chat_id%>, sender_id:<%= user.getId() %>, message: message}, function(data){
    			 document.getElementsByClassName("container-messages")[0].innerHTML=data;
    	     });
    		 
    		 $("#message").val("");
    	});
    });
    </script>
    
    <script type="text/javascript">
    function updateStatus() {
        var user_id = "<%= second_user.getId() %>";

        $.get("userStatus", { user_id: user_id }, function(data, status) {
            var element = document.querySelector('.second-user');
            if (element) {
                element.innerHTML = "<h3><%= second_user.getFirstName() %> <%= second_user.getLastName() %></h3>" + data ;
            }
            var second_element = document.querySelector('.wrap-account-user');
            if(second_element) {
                second_element.innerHTML = "<h3><%= second_user.getFirstName() %> <%= second_user.getLastName() %></h3>" + 
                                           "<h4>Department: <%= second_user.getContactInfo().get().getDepartment() %></h4>" +
                                           "<h4>Position: <%= second_user.getContactInfo().get().getPosition() %></h4>" + data;
            }

        });
    }

    setInterval(updateStatus, 500);
    </script>
    
    <script type="text/javascript">
    setInterval(updateStatusFriend, 2500); 
    
     function updateStatusFriend() {
    	   var currentUserId = "<%= user.getId() %>";
    	    var friendId = "<%= chat_user %>";

    	    $.get("checkFriendStatus", { currentUserId: currentUserId, friendId: friendId }, function(data) {
    	    	
  	    	  var element = document.querySelector('.friend-status');
	    	  if(element){
	    		  element.innerHTML= data + '<a href="#" id="delete-chat" onclick="deleteChat()">Delete chat</a>';
	    	  }
    	    });
     }
     
    function deleteUserFromContact() {
    	var current_id = "<%= user.getId() %>";
 	    var friend_id = "<%= chat_user %>";
 	    
    	$.post("deleteUserFromContact",{current_id:current_id, friend_id:friend_id}, function(data, status){
    		console.log("user with id: " + friend_id + " was removed from your contacts");
    	});
    	
    }
    
    function addUserToContact() {
    	var current_id = "<%= user.getId() %>";
 	    var friend_id = "<%= chat_user %>";
 	    
    	$.post("addUserToContacts",{current_id:current_id, friend_id:friend_id}, function(data, status){
    		console.log("user with id: " + friend_id + " was added to your contacts");
    	});
    	
    }
    
    function deleteChat() {
        var chat_id_db = "<%= chat_id %>";

        $.ajax({
            type: 'POST',
            url: 'deleteChat',
            data: {
                'chat_id_db': chat_id_db,
            },
            dataType: 'JSON',
            
            success: function(data) {
                var msg = data[0].msg;
                
                if (msg == 1) {
                    setTimeout(() => {
                        window.location.href = "mainPageUser.jsp?windowId=<%= windowId %>";
                    }, 500);
                } else if (msg == 3) {
                    console.log("Something went wrong...!");
                }
            },
            error: function(xhr, status, error) {
                console.error("An error occurred: " + status + " " + error);
            }
        });
        
    }
    </script>
    <script type="text/javascript">
    const imgFile = document.getElementById('img-file');
    const inputFile = document.getElementById('input-file');
    const modalWrapper = document.getElementById('modal-wrapper');
    const txtPreview = document.getElementById('txt-preview');
    const txtName = document.getElementById('txt-name');
    const modalTitle = document.getElementById('modal-title');
    const shadow_elem = document.getElementById('shadow');
    const closeButton = document.getElementById('close-icon');

    inputFile.onchange = function() {
    	const file = inputFile.files[0];
    	if (file) {
    		const fileType = file.type;

    		if (fileType.startsWith('image/')) {
    			imgFile.src = URL.createObjectURL(file);
    			imgFile.style.display = 'block';
    			txtPreview.style.display = 'none';
    			modalTitle.textContent = 'Send Image';
    		} else if (fileType === 'text/plain') {
    			txtName.textContent = file.name;
    			imgFile.style.display = 'none';
    			txtPreview.style.display = 'block';
    			modalTitle.textContent = 'Send Text File';
    		}

    		modalWrapper.classList.add('active');
    	}
    };

    shadow_elem.addEventListener('click', () => {
    	modalWrapper.classList.remove('active');
    	modalTitle.textContent = '';
    	inputFile.value = "";
    	imgFile.src = "";
    	txtName.textContent = "";
    });

    closeButton.addEventListener('click', () => {
    	modalWrapper.classList.remove('active');
    	modalTitle.textContent = '';
    	inputFile.value = "";
    	imgFile.src = "";
    	txtName.textContent = "";
    });

    document.querySelector('.blob-window').addEventListener('click', (event) => {
    	event.stopPropagation();
    });
    
    function sendData(){
    	var form = document.getElementById('file-form');
        var formData = new FormData(form);
        $.ajax({
            type: "POST",
            url: "sendfiletogroup",
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'JSON',
      
            success: function(data) {
            	console.log("success");
            	shadow_elem.click();
            },
            error: function(jqXhr, textStatus, errorMessage) {
            	shadow_elem.click();
            	console.log("Error: ", jqXhr);
            	}
            });   
        }
    </script>
    
    <script type="text/javascript">
    const wrapper_borders = document.getElementById('wrapper-borders');
    
    function add_emoji() {
    	wrapper_borders.classList.add('active');
    }
    
    wrapper_borders.addEventListener('click', () => {
    	wrapper_borders.classList.remove('active');
    });
    
    document.querySelector('.smile-window').addEventListener('click', (event) => {
    	event.stopPropagation();
    });
    
    function emoji_click(emoji_face) {
    	
    	var message_elem = document.getElementById('message');
    	
    	message_elem.value += emoji_face;
    }
    </script>
</body>
</html>