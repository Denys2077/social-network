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
    
    if (user == null) {
    	 response.sendRedirect("index.jsp");
    	 return;
    }
    
    String greeting = (String) session.getAttribute("greeting");
    boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page</title>
<link href="${pageContext.request.contextPath}/css/styleMainPage.css" rel="stylesheet" > 
<!-- SweetAlert2 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.2.0/sweetalert2.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/7.2.0/sweetalert2.all.min.js"></script>
<style>
     <%@ include file="/css/styleMainPage.css"%>
</style>
</head>
<body>
<% if(isAdmin) { %>
	<header>
		<h2 class="logo-chat">Chat</h2>
		<nav class="navigation">
			<a href="#" id="home">Home</a> 
			<a href="#" id="about">About</a> 
			<a href="#" id="contact">Contact</a>
			<button class="btnLogin" id="usersManager">Menu</button>
		</nav>
	</header>
	
	 <div class="wrapper-home">
        <span class="icon-close-home"><ion-icon name="close"></ion-icon></span>
        <h2>Admins are responsible for creating, updating and deleting users from the system.</h2>
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
    <!--
	<div class="wrapper" id="manager-action">
		<span class="close-icon"><ion-icon name="close"></ion-icon></span>
		<div class="form-action">
			<h2>Please, choose your operation</h2>
			<div class="manager-action">
				<button class="btnAction" id="addUsers">Add New Users</button>
				<button class="btnAction" id="lookUsers">Look through Existing Users</button>
			</div>
		</div>
	</div> -->
	<div class="modal-window">
	<div class="shadow-window">
	    <div class="user-window">
	        <div class="header">
	            <img class="photo" src="files/unknown_user.png" alt="img">
	            <h4>Administrator</h4>
	            <h3><%= user.getFirstName() %> <%= user.getLastName() %></h3>
	        </div>
	        <div class="links-user">
	            <a href="#" id="addUsers">Add New Users</a>
	            <a href="#" id="lookUsers">Look at Users</a>
	            <a href="#" onclick="logout()">Logout</a>
	        </div>
	    </div>
	</div>
</div>
	<% } %>
    <div class="modal_wrapper">
	    <div class="shadow">
	        <div class="greeting-wrap">
	            <span class="modal_icon"><ion-icon name="heart-outline"></ion-icon></span>
	            <p><%= greeting %></p>
	        </div>
	    </div>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
	
	<!--<script src="js/mainPage.js"></script> -->
	
	<script type="text/javascript">
	  const button = document.getElementById('usersManager');
      
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
    
    const buttonLookUser = document.getElementById('lookUsers');
    buttonLookUser.addEventListener('click', () => {
    	window.location.href = "listPage.jsp?windowId=<%= windowId %>";
    });
    const buttonAddUsers = document.getElementById('addUsers');
    buttonAddUsers.addEventListener('click', () => {
    	window.location.href = "registration.jsp?windowId=<%= windowId %>";
    });
    </script>
    
    <script type="text/javascript">
    /** 
     * Creating "About" wrapper
     */
    const wrapperAbout = document.querySelector('.wrapper-about');
    const buttonAbout = document.getElementById('about');
    const iconCloseAbout = document.querySelector('.icon-close-about');

    buttonAbout.addEventListener('click', ()=> {
    	wrapperAbout.classList.add('active-about');
    });

    iconCloseAbout.addEventListener('click', ()=> {
    	wrapperAbout.classList.remove('active-about');
    });

    /**
     * Home
     */
    const wrapperHome= document.querySelector('.wrapper-home');
    const buttonHome = document.getElementById('home');
    const iconCloseHome = document.querySelector('.icon-close-home');

    buttonHome.addEventListener('click', ()=> {
    	wrapperHome.classList.add('active-home');
    });

    iconCloseHome.addEventListener('click', ()=> {
    	wrapperHome.classList.remove('active-home');
    });

    /**
     * Contact
     */
    const wrapperContact = document.querySelector('.wrapper-contact');
    const buttonContact = document.getElementById('contact');
    const iconCloseContact = document.querySelector('.icon-close-contact');

    buttonContact.addEventListener('click', ()=> {
    	wrapperContact.classList.add('active-contact');
    });

    iconCloseContact.addEventListener('click', ()=> {
    	wrapperContact.classList.remove('active-contact');
    });
    
    var msg = null;
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