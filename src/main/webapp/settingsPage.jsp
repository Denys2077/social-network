<%@page import="com.denys.dw.user.addition.SocialMedia"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.denys.dw.dao.UserDAO"%>
<%@page import="com.denys.dw.dao.AbstractDAO"%>
<%@page import="com.denys.dw.user.addition.ContactInfo"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
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

AbstractDAO<User> dao = new UserDAO();
User full_info_user = dao.findEntityById(user.getId());

/* Відображення фото юзера */
Blob photoBlob = full_info_user.getPhoto();
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

ContactInfo contactInfo = null; 
if(full_info_user != null && full_info_user.getContactInfo().get() != null) {  
    contactInfo =  full_info_user.getContactInfo().get(); 
}

DateTimeFormatter  format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
String date_toString = full_info_user.getBirth().get().getDate().format(format);
request.setAttribute("formattedDate", date_toString);

SocialMedia media = null;
if(full_info_user != null && full_info_user.getSocialMedia().isPresent()) {  
	media =  full_info_user.getSocialMedia().get(); 
}

if(sessionData.containsValue(user)) {
	sessionData.put(windowId, full_info_user);
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
    <%@ include file="/css/styleSettingsPage.css" %>
</style>
</head>
<body>
    <div class="back-wrapper">
        <div class="go-back">
            <span class="icon-back" id="go-back-icon"><ion-icon name="arrow-back-outline"></ion-icon></span>
        </div>
    </div>
    
     <div class="wrapper">
         <div class="left-column-wrap">
             <div class="top-wrapper">
                 <div class="user-photo-wrap">
                     <% if(full_info_user != null) { %>
                        <% if(full_info_user.getPhoto() != null) { %>
                            <img class="photo" src="data:image/jpeg;base64, <%= photoBase64 %>">
                        <% } else { %>
                            <img class="photo" src="files/unknown_user.png" alt="img">
                        <% } %> 
                        <h2><%= full_info_user.getFirstName() %> <%= full_info_user.getLastName() %></h2>
                    <% } %>
                </div> 
             </div>
             <div class="low-wrapper">
                 <div class="links-wrap">
                     <a href="#" class="btn" id="account">Account</a>
                     <a href="#" class="btn" id="contact">Contact</a>
                     <a href="#" class="btn" id="address">Address</a>
                     <a href="#" class="btn" id="birth">Birth</a>
                     <a hre="#" class="btn" id="social_media">Social Media</a>
                 </div>
             </div>
         </div>
         
          <div class="nothing-chosen active">
            <h2>Please, choose menu</h2>
        </div>
        <!-- First Wrapper Window -->
         <div class="right-column-wrap-account">
                     <div class="account-wrap">
                <div class="header">
                    <h2>Account Settings</h2>
                </div>
                <form action="#" method="POST" enctype="multipart/form-data" id="updateAccountForm">

                <input type="hidden" value="<%= full_info_user.getId() %>" name="user_id"/>
                <input type="hidden" value="<%= full_info_user.getFirstName() %>" name=firstName />
                <input type="hidden" value="<%= full_info_user.getLastName() %>" name=lastName />
                
                <div class="img-area">
                    <% if(full_info_user != null) { %>
                        <% if(full_info_user.getPhoto() != null) { %>
						    <img class="photo" src="data:image/jpeg;base64, <%= photoBase64 %>" id="profile-pic-not-null">
						<%
						} else {
						%>
                             <img class="photo" src="files/unknown_user.png" alt="img" id="profile-pic">
                        <% } %>         
                    <% } %>
                </div>
                <div class="button-area">
                    <label for="input-file">update image</label>
                    <input type="file" accept="image/jpeg, image/png, image/jpg" id="input-file" name="inputPhoto">
                </div>
				<div class="input-line">
					<div class="input-container">
					 <% if(user != null) { %>
						<div class="input-item">
						    <span class="icon"><ion-icon name="person"></ion-icon></span>
							<input type="text" placeholder="Username" name="username" value="<%= full_info_user.getUsername() %>">
							<label>Username</label>
						</div>
						<div class="input-item">
						    <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
							<input type="password" placeholder="Password" name="password" value="<%= full_info_user.getPassword() %>">
							<label>Password</label>
						</div>
						<div class="input-item">
						    <span class="icon"><ion-icon name="person-outline"></ion-icon></span>
							<p class="unchosen-info-p"><%= full_info_user.getFirstName() %></p>
						    <label class="unchosen-info-label">First Name</label>
							
						</div>
						<div class="input-item">
						    <span class="icon"><ion-icon name="person-outline"></ion-icon></span>
							<p class="unchosen-info-p"><%= full_info_user.getLastName() %></p>
						    <label class="unchosen-info-label">Last Name</label>
						</div>
						<% } %>
					</div>
				</div>
				<div class="btns-area">
				    <div class="update-cancel-wrap">
				        <a href="#" id="update_account" onclick="update_account()">Update</a>
				        <a href="#" id="cancel_account">Cancel</a>
				    </div>
				</div>
                </form>
			</div>
         </div>
         <!-- Second Wrapper Window -->
          <div class="right-column-wrap-contact">
            <div class="header">
                <h2>Contact Info Settings</h2>
            </div>
            <form action="#" name="updateContactForm" id="updateContactForm">

            <input type="hidden" value="<%= full_info_user.getId() %>" name="user_id"/>
            <input type="hidden" value="<%= contactInfo.getDepartment() %>" name=department />
            <input type="hidden" value="<%= contactInfo.getPosition() %>" name=position />
            
            <div class="input-line">
                <div class="input-container">
                    <% if(contactInfo != null) { %>
                    <div class="input-item">
						<span class="icon"><ion-icon name="mail"></ion-icon></span> 
					    <input type="email" name="email" placeholder="Email" value="<%= contactInfo.getEmail()%>">
						<label>Email</label>
				    </div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="call"></ion-icon></span>
					    <input type="tel" name="telephone" placeholder="Telephone" value="<%= contactInfo.getTelephone()%>">
						<label>Telephone</label>
				    </div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="accessibility"></ion-icon></span>
						<p class="unchosen-info-p"><%= contactInfo.getPosition()%></p>
						<label class="unchosen-info-label">Position</label>
				    </div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="accessibility"></ion-icon></span>
						<p class="unchosen-info-p"><%= contactInfo.getDepartment()%></p>
						<label class="unchosen-info-label">Department</label>
				    </div>
				 <% } %>
                </div>
            </div>
            <div class="btns-area">
		        <div class="update-cancel-wrap">
				     <a href="#" id="update_contact_info" onclick="update_contactInfo()">Update</a>
				     <a href="#" id="cancel_contact_info">Cancel</a>
				</div>
			</div>
			</form>
        </div>
        <!-- Third Wrapper Window -->
          <div class="right-column-wrap-address">
            <div class="header">
                <h2>Address Settings</h2>
            </div>
             <form action="#">
              <input type="hidden" value="<%= 1 %>" name="user_id"/>
             <div class="input-line">
                <div class="input-container">
                    <div class="input-item">
						<span class="icon"><ion-icon name="globe"></ion-icon></span> 
						<p class="unchosen-info-p"><%= full_info_user.getAddress().get().getCountry() %></p>
						<label class="unchosen-info-label">Country</label>
				    </div>
				    <div class="input-item"></div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="globe"></ion-icon></span> 
						<p class="unchosen-info-p"><%= full_info_user.getAddress().get().getCity() %></p>
						<label class="unchosen-info-label">City</label>
				     </div>
				     <div class="input-item"></div>
				     <div class="input-item">
						<span class="icon"><ion-icon name="globe"></ion-icon></span> 
						<p class="unchosen-info-p"><%= full_info_user.getAddress().get().getStreet() %></p>
						<label class="unchosen-info-label">Street</label>
				    </div>
                </div>
            </div>
			</form>
        </div>
        <!-- Forth Window Wrapper -->
         <div class="right-column-wrap-birth">
            <div class="header">
                <h2>Birth Settings</h2>
            </div>
            <form action="#">
            
            <div class="input-line">
                <div class="input-container">
                    <div class="input-item">
                        <span class="icon"><ion-icon name="transgender-outline"></ion-icon></span>
                        <p class="unchosen-info-p"><%= full_info_user.getBirth().get().getSex().toString() %></p>
						<label class="unchosen-info-label">Sex</label>
				    </div>
				    <div class="input-item"></div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="balloon"></ion-icon></span> 
				        <p class="unchosen-info-p"><%= (String) request.getAttribute("formattedDate") %></p>
						<label class="unchosen-info-label">Date of Birth</label>
				    </div>
                </div>
            </div>
			</form>
        </div>
         <!-- Forth Window Wrapper -->
         <div class="right-column-wrap-socialMedia">
            <div class="header">
                <h2>Social Media Settings</h2>
            </div>
            <form action="#" name="updateSocialMediaForm" id="updateSocialMediaForm">
            
                <input type="hidden" value="<%= full_info_user.getId() %>" name="user_id"/>
                
                <div class="input-line">
                <div class="input-container">
                    <% if(media == null) { %>
                    <div class="input-item">
                        <span class="icon"><ion-icon name="logo-linkedin"></ion-icon></span> 
					    <input type="text" name="linkedInURL" placeholder="LinkedIn">
						<label>LinkedIn</label>
				    </div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="logo-instagram"></ion-icon></span> 
					    <input type="text" name="instagramURL" placeholder="Instagram">
					    <label>Instagram</label>
				    </div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="logo-facebook"></ion-icon></span> 
					    <input type="text" name="facebookURL" placeholder="Facebook">
					    <label>Facebook</label>
				    </div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="logo-tumblr"></ion-icon></span> 
					    <input type="text" name="telegramURL" placeholder="Telegram">
					    <label>Telegram</label>
				    </div>
				    <% } else { %>
				   <div class="input-item">
                        <span class="icon"><ion-icon name="logo-linkedin"></ion-icon></span> 
					    <input type="text" name="linkedInURL" placeholder="LinkedIn" value="<%= media.getLinkedInURL() %>">
						<label>LinkedIn</label>
				    </div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="logo-instagram"></ion-icon></span> 
					    <input type="text" name="instagramURL" placeholder="Instagram" value="<%= media.getInstagramURL() %>">
					    <label>Instagram</label>
				    </div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="logo-facebook"></ion-icon></span> 
					    <input type="text" name="facebookURL" placeholder="Facebook" value="<%= media.getFacebookURL() %>">
					    <label>Facebook</label>
				    </div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="logo-tumblr"></ion-icon></span> 
					    <input type="text" name="telegramURL" placeholder="Telegram" value="<%= media.getTelegramURL() %>">
					    <label>Telegram</label>
				    </div>
				    <% } %>
                </div>
            </div>
            <div class="btns-area">
			    <div class="update-cancel-wrap">
				    <a href="#" id="update_socialMedia" onclick="update_socialMedia()">Update</a>
				    <a href="#" id="cancel_socialMedia">Cancel</a>
				</div>
		    </div>
			</form>
        </div>
        <!-- End -->
     </div>
     
     <div class="modal_wrapper">
	    <div class="shadow">
	        <div class="success_wrap">
	            <span class="modal_icon"><ion-icon name="checkmark-outline"></ion-icon></span>
	            <p>User <%= user.getFirstName() %> <%= user.getLastName() %> was successfully updated</p>
	        </div>
	        <div class="error_wrap">
	            <span class="modal_icon"><ion-icon name="close"></ion-icon></span>
	            <p>Something went wrong during updating current user</p>
	        </div>
	    </div>
	</div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <script type="text/javascript">
    /* Функція добавлення фото */
    let profile_pic = document.getElementById('profile-pic');
    let input_file = document.getElementById('input-file');
    let profile_pic_not_null = document.getElementById('profile-pic-not-null');

    input_file.onchange = () => {
        if(profile_pic_not_null !== null) {
            profile_pic_not_null.src = URL.createObjectURL(input_file.files[0]);
        } else {
            profile_pic.src = URL.createObjectURL(input_file.files[0]);
        }
    }
    /**
     * Go back icon
     */
     const back_button = document.getElementById('go-back-icon');
     back_button.addEventListener('click', () => {
     	setTimeout(() => {
    		window.location.href = "mainPageUser.jsp?windowId=<%= windowId %>";
		}, 100);
     });
    /**
     * Buttons
     */
    const accountButton = document.getElementById('account');
    const contactButton = document.getElementById('contact');
    const address = document.getElementById('address');
    const birth = document.getElementById('birth');
    const social_media = document.getElementById('social_media');

    /** 
     *  Wrappers 
     */
    const nothing_chosen = document.querySelector('.nothing-chosen');
    const right_column_wrap_account = document.querySelector('.right-column-wrap-account');
    const right_column_wrap_contact = document.querySelector('.right-column-wrap-contact');
    const right_column_wrap_address = document.querySelector('.right-column-wrap-address');
    const right_column_wrap_birth = document.querySelector('.right-column-wrap-birth');
    const right_column_wrap_social_media = document.querySelector('.right-column-wrap-socialMedia');

    /* Натискаємо на кнопку 'Account' */
    accountButton.addEventListener('click', () => {
    	accountButton.classList.add('active');
    	right_column_wrap_account.classList.add('active');
    	removeActiveIdentity(contactButton, address, birth, social_media, nothing_chosen, right_column_wrap_contact, right_column_wrap_address, right_column_wrap_birth,right_column_wrap_social_media);
    });
    /* Натискаємо на кнопку 'Contact' */
    contactButton.addEventListener('click', () => {
    	contactButton.classList.add('active');
    	right_column_wrap_contact.classList.add('active');
    	removeActiveIdentity(accountButton, address, birth, social_media, nothing_chosen, right_column_wrap_account, right_column_wrap_address, right_column_wrap_birth,right_column_wrap_social_media);
    });
    /* Натискаємо на кнопку 'Address' */
    address.addEventListener('click', () => {
    	address.classList.add('active');
    	right_column_wrap_address.classList.add('active');
    	removeActiveIdentity(accountButton, contactButton, birth, social_media, nothing_chosen, right_column_wrap_account, right_column_wrap_contact, right_column_wrap_birth,right_column_wrap_social_media);
    });
    /* Натискаємо на кнопку 'Birth' */
    birth.addEventListener('click', () => {
    	birth.classList.add('active');
    	right_column_wrap_birth.classList.add('active');
    	removeActiveIdentity(accountButton, contactButton, address, social_media, nothing_chosen, right_column_wrap_account, right_column_wrap_contact, right_column_wrap_address,right_column_wrap_social_media);
    });
    /* Натискаємо на кнопку 'Social Media' */
    social_media.addEventListener('click', () => {
    	social_media.classList.add('active');
    	right_column_wrap_social_media.classList.add('active');
    	removeActiveIdentity(accountButton, contactButton, address, birth, nothing_chosen, right_column_wrap_account, right_column_wrap_contact, right_column_wrap_address,right_column_wrap_birth);
    });
    
    function removeActiveIdentity(button_first, button_second, button_third, button_forth, wrap_first, wrap_second, wrap_third, wrap_forth, wrap_fifth) {
    	if(button_first !== null) {
    		button_first.classList.remove('active');
    	}
    	
    	if(button_second !== null) {
    		button_second.classList.remove('active');
    	}
    	
    	if(button_third !== null) {
    		button_third.classList.remove('active');
    	}
    	
    	if(button_forth !== null) {
    		button_forth.classList.remove('active');
    	}
    	
    	if(wrap_first !== null) {
    		wrap_first.classList.remove('active');
    	}
    	
    	if(wrap_second !== null) {
    		wrap_second.classList.remove('active');
    	}
    	
    	if(wrap_third !== null) {
    		wrap_third.classList.remove('active');
    	}
    	
    	if(wrap_forth !== null) {
    		wrap_forth.classList.remove('active');
    	}
    	
    	if(wrap_fifth !== null) {
    		wrap_fifth.classList.remove('active');
    	}
    }
    /* Кнопка Cancel */
    /* Реалізація 'Cancel' для форми 'Account' */
    document.getElementById('cancel_account').addEventListener('click', (e) => {
    	e.preventDefault();
    	document.getElementById('updateAccountForm').reset();
    });
    /* Реалізація 'Cancel' для форми 'Contact' */
    document.getElementById('cancel_contact_info').addEventListener('click', (e) => {
    	e.preventDefault();
    	document.getElementById('updateContactForm').reset();
    });
    /* Реалізація 'Cancel' для форми 'Social Media' */
    document.getElementById('cancel_socialMedia').addEventListener('click', (e) => {
    	e.preventDefault();
    	document.getElementById('updateSocialMediaForm').reset();
    });
    </script>
    
    <!-- Реалізація функція, які відповідають за процес оновлення даних -->
    <script type="text/javascript">
    /* Елементи для модульного вікна*/
    const modal_wrapper = document.querySelector('.modal_wrapper');
    const success_wrap = document.querySelector('.success_wrap');
    const error_wrap = document.querySelector('.error_wrap');
    const shadow = document.querySelector('.shadow');
    
    shadow.addEventListener('click', () => {
    	modal_wrapper.classList.remove("active");
    	success_wrap.classList.remove("active");
    	error_wrap.classList.remove("active");
    });
    /* Функція оновлення даних при натисканні на Update Account*/
    function update_account() {
    	var form = document.getElementById('updateAccountForm');
        var formData = new FormData(form);
        
        $.ajax({
            type: "POST",
            url: "updateAccount",
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'JSON',
      
           success: function(data) {
               msg = data[0].msg;
              console.log(msg);

              if (msg == 1) {
                  modal_wrapper.classList.add("active");
                  success_wrap.classList.add("active");
                  setTimeout(() => {
			    		location.reload();
			       }, 3000);
              } else if (msg == 3) {
                  modal_wrapper.classList.add("active");
                  error_wrap.classList.add("active");
              }
          },  error: function(jqXhr, textStatus, errorMessage) {
              console.log("Error: ", jqXhr);
          }
      });   
  } 
    /* Функція оновлення даних 'Contact Information' */
    function update_contactInfo() {
    	 var data = $("#updateContactForm").serialize();
         
         $.ajax({
      	   
      	   type: "POST",
             url: "updateContactInfo",
             data: data,
             dataType: 'JSON',
             
             success:function(data) {
          	   msg = data[0].msg;
          	   
          	   if (msg == 1) {
                     modal_wrapper.classList.add("active");
                     success_wrap.classList.add("active");
                     setTimeout(() => {
     					location.reload();
     				}, 3000);
                 } else if (msg == 3) {
                     modal_wrapper.classList.add("active");
                     error_wrap.classList.add("active");
                 }
             }, 
             error: function(jqXhr, textStatus, errorMessage) {
                 console.log("Error: ", jqXhr);
             }
         });
    }
    /* Функція оновлення даних 'Social Media Information' */ 
    function update_socialMedia() {
    	var data = $("#updateSocialMediaForm").serialize();
         
         $.ajax({
      	   
      	   type: "POST",
             url: "updateSocialMedia",
             data: data,
             dataType: 'JSON',
             
             success:function(data) {
          	   msg = data[0].msg;
          	   
          	   if (msg == 1) {
                     modal_wrapper.classList.add("active");
                     success_wrap.classList.add("active");
                     setTimeout(() => {
     					location.reload();
     				}, 3000);
                 } else if (msg == 3) {
                     modal_wrapper.classList.add("active");
                     error_wrap.classList.add("active");
                 }
             }, 
             error: function(jqXhr, textStatus, errorMessage) {
                 console.log("Error: ", jqXhr);
             }
         });
    }
    </script>
</body>
</html>