<%@page import="java.util.UUID"%>
<%@ page import="com.denys.dw.resource.ConfigurationManager"%>
<%@ page import="com.denys.dw.resource.MessageManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // Generating unique window id if it doesn't exist
    String windowId = (String) session.getAttribute("windowId");
    if(windowId == null) {
    	windowId = UUID.randomUUID().toString();
    	session.setAttribute("windowId", windowId);
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Start</title>
<!-- SweetAlert2 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.2.0/sweetalert2.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.2.0/sweetalert2.all.min.js"></script>
<style>
    <%@ include file="/css/styleStartPage.css" %>
</style>
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
<input type="hidden" id="message" value="<%= request.getAttribute("message") %>" />
    <header>
        <h2 class="logo-chat">Chat</h2>
        <nav class="navigation">
            <a href="#" id="home">Home</a>
            <a href="#" id="about">About</a>
            <a href="#" id="contact">Contact</a>
            <button class="btnLogin">Login</button>
        </nav>
    </header>
    
    <div class="wrapper-home">
        <span class="icon-close-home"><ion-icon name="close"></ion-icon></span>
        <h2>Please, log in to get access to additional functional</h2>
    </div>
    
     <div class="wrapper-about">
        <span class="icon-close-about"><ion-icon name="close"></ion-icon></span>
		<div class="form-about">
			<h2>Welcome to our application!</h2>
			<p>Thanks this app, you'll be able to communicate with your
				colleague. Simplicity of the messenger helps to keep in touch with
				your employees/workers/colleagues wherever you're.</p>
		</div>
	</div>
    
     <div class="wrapper-contact">
        <span class="icon-close-contact"><ion-icon name="close"></ion-icon></span>
        <div class="form-contact">
            <h2>Contact</h2>
            <p>Developer: Ovcharenko Denys</p>
            <p>Tel: +380681234879</p>
            <p>Telegram: http://www.telegram.com/telega</p>
            <p>Instagram: http://www.instagram.com/insta</p>
        </div>
    </div>
    
    <div class="wrapper">
        <span class="icon-close"><ion-icon name="close"></ion-icon></span>
        <div class="form-login">
            <h2>Login</h2>
            <form name="loginForm" id="loginForm">
                <input type="hidden" name="windowId" value="<%= windowId %>" />
                <div class="input-box">
                    <span class="icon"><ion-icon name="person"></ion-icon></span>
                    <input type="text" required name="username" required>
                    <label>Username</label>
                </div>
                <div class="input-box">
                    <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
                    <input type="password" required name="password" required>
                    <label>Password</label>
                </div>
                <div class="remeber-enter">
                    <label><input type="checkbox" name="remember">Remember me</label>
                    <label><input type="checkbox" name="asAdmin">Enter as an administrator</label>
                </div>
                <button type="button" class="btn" onclick="login()">Login</button>
            </form>
        </div>
    </div>
    
    <div class="modal_wrapper">
	    <div class="shadow">
	        <div class="check_mark_wrap">
	            <span class="modal_icon"><ion-icon name="checkmark-outline"></ion-icon></span>
	            <p><%= MessageManager.getProperty("message.needEnterAsAdmin") %>.</p>
	        </div>
	        <div class="admin_found_wrap">
	            <span class="modal_icon"><ion-icon name="close"></ion-icon></span>
	            <p><%= MessageManager.getProperty("message.admin_no_exist") %></p>
	        </div>
	        <div class="user_found_wrap">
	            <span class="modal_icon"><ion-icon name="close"></ion-icon></span>
	            <p><%= MessageManager.getProperty("message.user_was_not_found") %></p>
	        </div>
	    </div>
	</div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    
    <script src="js/startPage.js"></script>
    <script>

    const modal_wrapper = document.querySelector('.modal_wrapper');
    const shadow = document.querySelector('.shadow');
    const check_mark_wrap = document.querySelector('.check_mark_wrap');
    const admin_found_wrap = document.querySelector('.admin_found_wrap');
    const user_found_wrap = document.querySelector('.user_found_wrap');
    
    shadow.addEventListener('click', () => {
    	modal_wrapper.classList.remove("active");
    	check_mark_wrap.classList.remove("active");
    	admin_found_wrap.classList.remove("active");
    	user_found_wrap.classList.remove("active");
    });
    
    var msg = null;
    
    function login() {
    	var data = $('#loginForm').serialize();
    	
    	$.ajax({
    		
    		type : 'POST',
    		url : 'login',
    		data : data,
    		dataType : 'JSON',
    		
    		success:function(data) {
    			
    			msg = data[0].msg;
    			var windowId = data[0].windowId;
    			
    			window.console && console.log(msg);
    			
    			if (msg == 1) {
    				window.location.replace("mainPage.jsp?windowId=" + windowId);
    			} else if (msg == 3) {
    				modal_wrapper.classList.add("active");
    				check_mark_wrap.classList.add("active");
    			} else if (msg == 5) {
    				modal_wrapper.classList.add("active");
    				admin_found_wrap.classList.add("active");
    			} else if (msg == 7) {
    				window.location.replace("mainPageUser.jsp?windowId=" + windowId);
    			} else if (msg == 9) {
    				modal_wrapper.classList.add("active");
    				user_found_wrap.classList.add("active");
    			}
    			
    		}
    	});
    }
    </script>
</body>
</html>