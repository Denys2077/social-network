<%@page import="com.denys.dw.user.User"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
<link href="${pageContext.request.contextPath}/css/styleRegistrationPage.css" rel="stylesheet" > 
<style>
    <%@ include file="/css/styleRegistrationPage.css" %>
</style>
</head>
<body>
	 <form class="form" action="#" name="registrationForm" id="registrationForm">
		<input type="hidden" name="command" value="registration" />
		<h2 class="h2-header">Sign Up</h2>
		<!-- Progress Bar -->
		<div class="progressbar">
		    <div class="progress" id="progress"></div>
		    <div class="progress-step active" data-title="User"></div>
		    <div class="progress-step" data-title="Contact"></div>
		    <div class="progress-step" data-title="Address"></div>
		    <div class="progress-step" data-title="Birth"></div>
		    <div class="progress-step" data-title="Finish"></div>
		</div>
		<div class="form-step active">
			<div class="input-box">
				<span class="icon"><ion-icon name="person"></ion-icon></span> <input
					type="text" name="username" id="username"> <label>Username</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="person-outline"></ion-icon></span>
				<input type="text" name="firstName" id="firstname"> <label>First
					Name</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="person-outline"></ion-icon></span>
				<input type="text" name="lastName" id="lastname"> <label>Last Name</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="lock-closed"></ion-icon></span> <input
					type="password" name="password" id="password"> <label>Password</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="lock-closed"></ion-icon></span> <input
					type="password" name="confirmPass" id="confirmPass"> <label>Confirm
					Password</label>
			</div>
			<div class="">
				<a href="#" class="btn btn-next" onclick="userinfo()">Next</a>
			</div>
			<div class="come-back">
				<p>
					Do you want to go back? <a href="mainPage.jsp?windowId=<%= windowId %>"  class="come-back-link">Go Back</a>
				</p>
			</div>
		</div>
		
		<div class="form-step">
			<div class="input-box">
				<span class="icon"><ion-icon name="mail"></ion-icon></span> 
				<input type="email" name="email" id="email"> 
				<label>Email</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="call"></ion-icon></span> 
				<input type="tel" name="telephone" id="telephone"> 
					<label>Telephone</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="accessibility"></ion-icon></span> 
				<input type="text" name="position" id="position"> <label>Position</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="accessibility"></ion-icon></span> 
				<input type="text" name="department" id="department"> 
				<label>Department</label>
			</div>
			<div class="btns-group">
				<a href="#" class="btn btn-prev">Previous</a> 
				<a href="#" class="btn btn-next">Next</a>
			</div>
			<div class="come-back">
				<p>
					Do you want to go back? <a href="mainPage.jsp?windowId=<%= windowId %>" class="come-back-link">Go Back</a>
				</p>
			</div>
		</div>
		
		<div class="form-step">
			<div class="input-box">
				<span class="icon"><ion-icon name="globe"></ion-icon></span> 
				<input type="text" name="country"> 
				<label>Country</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="globe"></ion-icon></span> 
				<input type="text" name="city"> 
					<label>City</label>
			</div>
			<div class="input-box">
				<span class="icon"><ion-icon name="globe"></ion-icon></span> 
				<input type="text" name="street"> 
				<label>Street</label>
			</div>
			<div class="btns-group">
				<a href="#" class="btn btn-prev">Previous</a> 
				<a href="#" class="btn btn-next">Next</a>
			</div>
			<div class="come-back">
				<p>
					Do you want to go back? <a href="mainPage.jsp?windowId=<%= windowId %>" class="come-back-link">Go Back</a>
				</p>
			</div>
		</div>
		
		<div class="form-step">
			<div class="input-box">
				<span class="icon"><ion-icon name="balloon"></ion-icon></span> 
				<input type="date" name="date"> 
				<label class="top-5">Date of Birth</label>
			</div>
			<div class="input-box">
				<select id="gender" name="gender">
					<option>Male</option>
					<option>Female</option>
					<option>Other</option>
				</select> 
				<label class="top-5">Sex</label>
			</div>
			<div class="btns-group">
				<a href="#" class="btn btn-prev">Previous</a> 
				<a href="#" class="btn btn-next">Next</a>
			</div>
			<div class="come-back">
				<p>
					Do you want to go back? <a href="mainPage.jsp?windowId=<%= windowId %>" class="come-back-link">Go Back</a>
				</p>
			</div>
		</div>

    <div class="form-step">
        <h2>Create account</h2>
        <p>Would you like to create new user?</p>
        <div class="btns-group">
				<a href="#" class="btn btn-prev">Previous</a> 
				<button type="button" id="done" class="btn btn-done" onclick="registration()">Done</button>
			</div>
			<div class="come-back">
				<p>
					Do you want to go back? <a href="mainPage.jsp?windowId=<%= windowId %>" class="come-back-link">Go Back</a>
				</p>
			</div>
    </div>
	</form>
	
	<div class="modal_wrapper">
	    <div class="shadow">
	        <div class="success_wrap">
	            <span class="modal_icon"><ion-icon name="checkmark-outline"></ion-icon></span>
	            <p>You have successfully created new user.</p>
	        </div>
	        <div class="warning_wrap">
	            <span class="modal_icon"><ion-icon name="alert"></ion-icon></span>
	            <p>Please, confirm your password.</p>
	        </div>
	        <div class="error_wrap">
	            <span class="modal_icon"><ion-icon name="close"></ion-icon></span>
	            <p>It's imposible to add existing user.</p>
	        </div>
	    </div>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <script type="text/javascript">
    /**
     * 
     */
    const prevBtns = document.querySelectorAll('.btn-prev');
    const nextBtns = document.querySelectorAll('.btn-next');
    const progress = document.getElementById('progress');
    const formSteps = document.querySelectorAll('.form-step');
    const progressSteps = document.querySelectorAll('.progress-step');

    const btn_done = document.querySelector('.btn-done');
    const modal_wrapper = document.querySelector('.modal_wrapper');
    const success_wrap = document.querySelector('.success_wrap');
    const warning_wrap = document.querySelector('.warning_wrap');
    const error_wrap = document.querySelector('.error_wrap');
    const shadow = document.querySelector('.shadow');

    let formStepsNum = 0;

    nextBtns.forEach(btn => {
    	btn.addEventListener('click', () => {
    		if(userinfo() != false) {
    			formStepsNum++;
        		updateFormSteps();
        		updateProgressBar();
    		}
    	});
    });

    prevBtns.forEach(btn => {
    	btn.addEventListener('click', () => {
    		if(userinfo() != false) {
    		    formStepsNum--;
    	    	updateFormSteps();
    	    	updateProgressBar();
    		}
    	});
    });

    function updateFormSteps() {
    	formSteps.forEach(formStep => {
    		formStep.classList.contains('active') && formStep.classList.remove('active');
    	});
    	formSteps[formStepsNum].classList.add('active');
    };

    function updateProgressBar() {
    	progressSteps.forEach((progressStep, ind) => {
    		if(ind < formStepsNum + 1) {
    			progressStep.classList.add('active');
    		} else {
    			progressStep.classList.remove('active');
    		}
    	})
    };

    /* Модальні вікна - можливо прийдеться переробити */  
    shadow.addEventListener('click', () => {
    	modal_wrapper.classList.remove("active");
    	success_wrap.classList.remove("active");
    	warning_wrap.classList.remove("active");
    	error_wrap.classList.remove("active");
    });
    
    /* Після цього буде реалізовано JSON і операції добавлення юзера */
    function userinfo() {
    	if($('#password').val() != $("#confirmPass").val()) {
    		modal_wrapper.classList.add("active");
    		warning_wrap.classList.add("active");
    		return false;
    	}
    }
  
    var msg = null;
    
    function registration() {
     	var data = $("#registrationForm").serialize();
    	$.ajax({
    		
    		type : 'POST',
    		url : 'registration',
    		data : data,
    		dataType : 'JSON',
    		
    		success:function(data) {
    			msg = data[0].msg;
    			
    			
    			window.console && console.log(msg);
    			
    			if(msg == 1) {
    				modal_wrapper.classList.add("active");
    				success_wrap.classList.add("active");
    	    		
    				setTimeout(() => {
    					location.reload();
    				}, 5000);
    				
    			} else if(msg == 3) {
    				modal_wrapper.classList.add("active");
    	    		error_wrap.classList.add("active");
    			}
    		},
    		   error: function(jqXhr, textStatus, errorMessage){
    			      console.log("Error: ", errorMessage);
    			   }
    	});	
    }
    </script>
</body>
</html>