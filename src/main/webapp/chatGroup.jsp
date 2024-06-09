<%@page import="java.util.Base64"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="com.denys.dw.dao.GroupChatDAO"%>
<%@page import="com.denys.dw.chat.GroupChat"%>
<%@page import="java.util.List"%>
<%@page import="com.denys.dw.dao.EmojiDAO"%>
<%@page import="com.denys.dw.chat.emoji.Emoji"%>
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
if(chat_id == null) {
	 chat_id = (String) request.getAttribute("chat_id_db");
}

AbstractDAO<Emoji> emojiDAO = new EmojiDAO();
List<Emoji> emoji_faces = emojiDAO.getAllEntities();

AbstractDAO<GroupChat> groupDAO = new GroupChatDAO();
GroupChat chat = groupDAO.findEntityById(Long.parseLong(chat_id));
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat Group</title>
<style>
    <%@ include file="/css/styleChatGroup.css"%>
</style>
</head>
<body>
    <header id="header-id">
        <h2 class="logo-chat">Chat</h2>
        <% if(chat.getGroup_photo() == null) { %>
            <img src="files/unknown_user.png" alt="img">
        <% } else {  %>
            <%
               Blob photoBlob = chat.getGroup_photo();
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
              <img src="data:image/jpeg;base64, <%= photoBase64 %>" id="profile-pic-not-null">
        <% } %>
        <div class="name-status-container">
        
        </div>
    </header>
    
      <div class="back-wrapper">
        <div class="go-back">
            <span class="icon-back" id="go-back-icon"><ion-icon name="arrow-back-outline"></ion-icon></span>
        </div>
    </div>
    
    <div class="container-messages">
        <!--<h2>30 May</h2>
        <div class="my-messages">
            <p class="name">Denys Ovcharenko</p>
            <img class="user-avatar" src="files/user2.jpg" alt="img">
            <p>Hello. How are You? I hope you're fine. May you send me a test?</p>
            <span class="time-message">12:29</span>
        </div>
        <div class="other-messages">
         <p class="name">Denys Ovcharenko</p>
            <img class="user-avatar" src="files/user2.jpg" alt="img">
            <p>Hello. How are You? I hope you're fine. May you send me a test?....&#129324; awdawdwad awd</p>
            <span class="time-message">12:29</span>
        </div>
      <div class="my-messages">
            <img class="user-avatar" src="files/user2.jpg" alt="img">
            <p>Can You give a hand? ara</p>
            <span class="time-message">12:29</span>
        </div>
         <div class="other-messages">
            <img class="user-avatar" src="files/user2.jpg" alt="img">
            <p>Hello. How are You? I hope you're fine. May you send me a test?....&#129324; awdawdwad awd</p>
            <span class="time-message">12:29</span>
        </div>
        <div class="my-messages">
            <img class="user-avatar" src="files/user2.jpg" alt="img">
            <p>Suka....&#129324;</p>
            <span class="time-message">12:29</span>
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
            <input type="hidden" name="chat_id_db" value="<%= chat_id %>">
            <input type="hidden" name="sender_id" value = "<%= user.getId() %>">
            <input type="hidden" name="message" value=" ">
        </form>
        <input id="message"type="text" name="message" placeholder="Message">
        <span class="add-smile" onclick="add_emoji()"><ion-icon name="happy-outline"></ion-icon></span>
        <span id="send" class="send-message"><ion-icon name="send-outline"></ion-icon></span>
    </div>
    
    <div class="outer-list-user-wrap" id="outer-list-user-wrap">
        <div class="shadow-list-user-wrap">
            <div class="list-user-wrap">
                <div class="photo-name-group">
                    <% if (chat.getCreater_id() == user.getId()) { %>
                    <div class="update-photo-wrap">
                        <form action="updatePhotoGroup" method="POST" enctype="multipart/form-data" id="photo-form">
                        <input type="hidden" name="chat_id" value="<%= chat_id %>">
                            <% if(chat.getGroup_photo() == null) { %>
                            	<label for="photo-input"><img src="files/unknown_user.png" alt="img" id="profile-pic"></label>
                            <% } else { %>
                                <%
                                Blob photoBlob = chat.getGroup_photo();
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
                                <label for="photo-input"><img class="photo" src="data:image/jpeg;base64, <%= photoBase64 %>" id="profile-pic-not-null"></label>
                            <% } %> 
                            <input type="file" accept="image/png, image/jpeg, image/jpg" id="photo-input" style="display: none" name="file">
                            <a href="#" onclick="update_photo()">Update photo group</a>
                        </form>
                    </div>
                    <div class="update-name-wrap">
                          <form action="updateNameGroup" method="POST" id="name-form-azaza">
                            <input type="hidden" name="chat_id" value="<%= chat_id %>">
                            <input type="text" placeholder="Group Name" name="name_group" value="<%= chat.getName_group() %>">
                           <!--  <a href="#" onclick="update_name()">Update Name</a>-->
                        </form>
                    </div>
                    <a class="delete-btn" href="#" onclick="delete_chat()">Delete chat</a>
                    <% } else { %>
                    <div class="form">
                        <% if(chat.getGroup_photo() == null) { %>
                            <img src="files/unknown_user.png" alt="img">
                        <% } else { %>
                        <%
                             Blob photoBlob = chat.getGroup_photo();
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
                            <img src="data:image/jpeg;base64, <%= photoBase64 %>" alt="img">
                        <% } %>
                         <h2><%= chat.getName_group() %></h2>
                    </div>
                    <a class="exit-btn" href="#" onclick="exit_chat()">Exit of chat</a>
                    <% } %>
                </div>
               
                <div class="list-users">
                <!--
                    <div class="user-info">
                        <img src="files/unknown_user.png" alt="">
                        <h3>Viktor Beliy</h3>
                        <span id="offline">Offline</span>
                        <span class="option-else" onclick="toggleOptionUser(1)"><ion-icon name="ellipsis-vertical-outline"></ion-icon></span>                  
                    </div>
                    <div class="user-info">
                        <img src="files/user2.jpg" alt="">
                        <h3>Svitlana Blinova</h3>
                        <span id="online">Online</span>
                        <span class="option-else" onclick="toggleOptionUser(2)"><ion-icon name="ellipsis-vertical-outline"></ion-icon></span>
                    </div>
                    <div class="user-info">
                        <img src="files/unknown_user.png" alt="">
                        <h3>Denys Ovcharenko</h3>
                        <span id="offline">Offline</span>
                        <span class="option-else" onclick="toggleOptionUser(3)"><ion-icon name="ellipsis-vertical-outline"></ion-icon></span>
                    </div>
                     <div class="user-info">
                        <img src="files/unknown_user.png" alt="">
                        <h3>Denys Ovcharenko</h3>
                        <span id="offline">Offline</span>
                        <span class="option-else" onclick="toggleOptionUser(4)"><ion-icon name="ellipsis-vertical-outline"></ion-icon></span>
                    </div>
                      -->
                </div>
            </div>
            <div class="outer-option-user-wrap">
                <div class="option-user">
                    <% if (chat.getCreater_id() == user.getId()) { %>
                         <a href="#">Delete from group chat</a>
                    <% } %>
                     
                    <a href="#">Create private chat</a>            
                    
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
    <!-- Код для повернення назад -->
    <script type="text/javascript">
    const icon_back = document.querySelector('.icon-back');
    
    icon_back.addEventListener('click', () => {
    	setTimeout(() => {
    		window.location.href = "mainPageUser.jsp?windowId=<%= windowId %>";
		}, 100);
    }); 
    </script>
    
    <script type="text/javascript">
    
    const header_id = document.getElementById('header-id');
    const outer_list_user_wrap = document.getElementById('outer-list-user-wrap');
    const shadow_list_user_wrap = document.querySelector('.shadow-list-user-wrap');
    const list_user_wrap = document.querySelector('.list-user-wrap');
    
    header_id.addEventListener('click', () => {
    	outer_list_user_wrap.classList.add('active');
    });
    
    shadow_list_user_wrap.addEventListener('click', () => {
    	outer_list_user_wrap.classList.remove('active');
    });
    
    list_user_wrap.addEventListener('click', (event) => {
    	event.stopPropagation();
    });
    
    
    //-----------------------------------------------------------------------------
    const outer_option_user_wrap = document.querySelector('.outer-option-user-wrap');
    const option_user = document.querySelector('.option-user');

    outer_option_user_wrap.addEventListener('click', (event) => {
    	event.stopPropagation();
        outer_option_user_wrap.classList.remove('active');
    });

    function toggleOptionUser(user_id) {
        outer_option_user_wrap.classList.add('active');

        const links = option_user.querySelectorAll('a');
        links.forEach((link, index) => {
            link.id = `link-${user_id}-${index + 1}`;

            // Перевіряємо текст посилання, щоб визначити його функцію
            if (link.textContent.trim() === 'Delete from group chat') {
                link.onclick = () => deleteFromGroupChat(user_id);
            } else if (link.textContent.trim() === 'Create private chat') {
                link.onclick = () => createPrivateChat(user_id);
            }
        });

        console.log(user_id);
    }

    function deleteFromGroupChat(user_id) {

    	$.post("deleteuserfromgroupchat", {chat_id_db:<%= chat_id %>, user_id:user_id}, function(data, status) {
    		console.log("deleted user with id = " + user_id);
    	});
    }

    function createPrivateChat(friend_id_value) {

    	const current_id_value = <%= user.getId() %>;

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
    </script>
    <!-- Емодзі -->
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
    <!-- Реалізація процесу додавання повідомлення -->
    <script type="text/javascript">
    setInterval(loadmessage, 1000);
    
    function loadmessage() {
    	
    	$.post("loadmessagestogroup",{chat_id_db:<%= chat_id %>, sender_id:<%= user.getId() %>}, function(data, status){
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
    		 var sender_id = <%= user.getId() %>;
    		 var chat_id_db = <%= chat_id %>;
    	     var message = $("#message").val();
    		 
    		 $.post("sendmessagetogroup", {chat_id_db: chat_id_db, sender_id: sender_id, message: message}, function(data){
    			 document.getElementsByClassName("container-messages")[0].innerHTML=data;
    		 });
    	     
    		 $("#message").val("");
    	});
    });
    </script>
    <!-- Реалізація процесу списку юзерів в групі -->
    <script type="text/javascript">
    setInterval(loadusers, 1000);
    
    function loadusers() {
    	$.post("loaduserstogroup",{chat_id_db:<%= chat_id %>, user_id:<%= user.getId() %>}, function(data, status){
    		document.getElementsByClassName("list-users")[0].innerHTML=data;
    		
    	});
    }
    </script>
    
    <!-- Реалізація процесу додавання фото/текстового повідомлення -->
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
        let profile_pic = document.getElementById('profile-pic');
        let input_file = document.getElementById('photo-input');
        let profile_pic_not_null = document.getElementById('profile-pic-not-null');

        if(input_file) {
        	   input_file.onchange = () => {
                   if(profile_pic_not_null !== null) {
                       profile_pic_not_null.src = URL.createObjectURL(input_file.files[0]);
                   } else {
                       profile_pic.src = URL.createObjectURL(input_file.files[0]);
                   }
               }
        }
     
         function update_photo() {
        	 var form = document.getElementById('photo-form');
             var formData = new FormData(form);
             $.ajax({
                 type: "POST",
                 url: "updatePhotoGroup",
                 data: formData,
                 processData: false,
                 contentType: false,
                 dataType: 'JSON',
           
                 success: function(data) {
                 	console.log("update photo - success");
                 },
                 error: function(jqXhr, textStatus, errorMessage) {
                 	console.log("update photo - Error: ", jqXhr);
                 	}
                 });   
         }
         
         function update_name() {
        	console.log("change name");
         }
         
         function delete_chat() {
        	 console.log("delete chat");
         }
         
         function delete_chat() {
        	 
        	 $.post("deleteGroupChat",{chat_id_db:<%= chat_id %>}, function(data, status) {
        		 setTimeout(() => {
                     window.location.href = "mainPageUser.jsp?windowId=<%= windowId %>";
                 }, 500);
         		
         	});
         }
         
         function exit_chat() {
        	 
        	 $.post("exitGroupChat",{chat_id_db:<%= chat_id %>, user_id:<%= user.getId() %>}, function(data, status) {
        		 setTimeout(() => {
                     window.location.href = "mainPageUser.jsp?windowId=<%= windowId %>";
                 }, 500);
         		
         	});
         }
         
    </script>
    
    <script type="text/javascript">
    setInterval(checkonlinestatus, 1000);
    function checkonlinestatus() {
    	var title = "<h2><%= chat.getName_group() %></h2>";
    	$.post("checkgrouponlineusers",{chat_id_db:<%= chat_id %>, user_id:<%= user.getId() %>}, function(data, status){
    		document.getElementsByClassName("name-status-container")[0].innerHTML= title + data;
    	});
    }
    </script>
</body>
</html>