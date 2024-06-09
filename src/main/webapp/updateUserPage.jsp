<%@page import="java.util.Map"%>
<%@page import="com.denys.dw.user.addition.ContactInfo"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.denys.dw.dao.UserDAO"%>
<%@page import="com.denys.dw.user.User"%>
<%@page import="com.denys.dw.dao.AbstractDAO"%>
<%

    String windowId = (String) request.getAttribute("windowId");
    if(windowId == null) {
        windowId = request.getParameter("windowId");
    }

    Map<String, Object> sessionData = (Map<String, Object>) session.getAttribute("sessionData");
    User current_user = null;

    if (sessionData != null && windowId != null) {
        current_user = (User) sessionData.get(windowId);
    }

    String id_toString = request.getParameter("userId");
    Long id = Long.parseLong(id_toString);
    AbstractDAO<User> dao = new UserDAO();
    User user = dao.findEntityById(id);
    DateTimeFormatter  format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String date_toString = user.getBirth().get().getDate().format(format);
	request.setAttribute("formattedDate", date_toString);
	
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update page</title>
<style>
     <%@ include file="/css/styleUpdateUserPage.css"%>
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
                    <% if(user != null) { %>
                        <% if(user.getPhoto() != null) { %>
                            <img class="photo" src="data:image/jpeg;base64, <%= photoBase64 %>">
                        <% } else { %>
                            <img class="photo" src="files/unknown_user.png" alt="img">
                        <% } %> 
                        <h2><%= user.getFirstName() %> <%= user.getLastName() %></h2>
                    <% } %>
                </div>  
            </div>
            <div class="low-wrapper">
                <div class="links-wrap">
                    <a href="#" class="btn" id="account">Account</a>
                    <a href="#" class="btn" id="contact">Contact</a>
                    <a href="#" class="btn" id="address">Address</a>
                    <a href="#" class="btn" id="birth">Birth</a>
                </div>
            </div>
        </div>
        
        <div class="nothing-chosen active">
            <h2>Please, choose menu</h2>
        </div>
        
        <div class="right-column-wrap-account">
            <div class="account-wrap">
                <div class="header">
                    <h2>Account Settings</h2>
                </div>
                <form action="updateAccount" method="POST" enctype="multipart/form-data" id="updateAccountForm">
                <input type="hidden" value="<%= user.getId() %>" name="user_id"/>
                <div class="img-area">
                      <% if(user != null) { %>
                        <% if(user.getPhoto() != null) { %>
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
							<input type="text" placeholder="Username" name="username" value="<%= user.getUsername() %>">
							<label>Username</label>
						</div>
						<div class="input-item">
						    <span class="icon"><ion-icon name="lock-closed"></ion-icon></span>
							<input type="password" placeholder="Password" name="password" value="<%= user.getPassword()%>">
							<label>Password</label>
						</div>
						<div class="input-item">
						    <span class="icon"><ion-icon name="person-outline"></ion-icon></span>
							<input type="text" placeholder="First Name" name="firstName" value="<%= user.getFirstName()%>">
						    <label>First Name</label>
						</div>
						<div class="input-item">
						    <span class="icon"><ion-icon name="person-outline"></ion-icon></span>
							<input type="text" placeholder="Last Name" name="lastName" value="<%= user.getLastName()%>">
							<label>Last Name</label>
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
        
        <div class="right-column-wrap-contact">
            <div class="header">
                <h2>Contact Info Settings</h2>
            </div>
            <form action="#" name="updateContactForm" id="updateContactForm">
            <input type="hidden" value="<%= user.getId() %>" name="user_id"/>
            <div class="input-line">
                <div class="input-container">
                    <% ContactInfo contactInfo = null; %>
                    <% if(user != null && user.getContactInfo().get() != null) {  %>
                    <% contactInfo =  user.getContactInfo().get(); %>
                    <div class="input-item">
						<span class="icon"><ion-icon name="mail"></ion-icon></span> 
					    <input type="email" name="email" placeholder="Email" value="<%= contactInfo.getEmail() %>">
						<label>Email</label>
				    </div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="call"></ion-icon></span>
					    <input type="tel" name="telephone" placeholder="Telephone" value="<%= contactInfo.getTelephone() %>">
						<label>Telephone</label>
				    </div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="accessibility"></ion-icon></span>
					    <input type="text" name="position" placeholder="Position" value="<%= contactInfo.getPosition() %>">
						<label>Position</label>
				    </div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="accessibility"></ion-icon></span>
					    <input type="text" name="department" placeholder="Department" value="<%= contactInfo.getDepartment() %>">
						<label>Department</label>
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
        
         <div class="right-column-wrap-address">
            <div class="header">
                <h2>Address Settings</h2>
            </div>
             <form action="#" name="updateAddressForm" id="updateAddressForm">
              <input type="hidden" value="<%= user.getId() %>" name="user_id"/>
             <div class="input-line">
                <div class="input-container">
                    <% if(user != null) {  %>
                    <div class="input-item">
						<span class="icon"><ion-icon name="globe"></ion-icon></span> 
					    <input type="text" placeholder="Country" name="country" value="<%= user.getAddress().get().getCountry() %>">
						<label>Country</label>
				    </div>
				    <div class="input-item"></div>
				    <div class="input-item">
						<span class="icon"><ion-icon name="globe"></ion-icon></span> 
					    <input type="text" placeholder="City" name="city" value="<%= user.getAddress().get().getCity() %>">
						<label>City</label>
				     </div>
				     <div class="input-item"></div>
				     <div class="input-item">
						<span class="icon"><ion-icon name="globe"></ion-icon></span> 
					    <input type="text" placeholder="Street" name="street" value="<%= user.getAddress().get().getStreet() %>">
						<label>Street</label>
				    </div>
				    <% } %>
                </div>
            </div>
            <div class="btns-area">
			    <div class="update-cancel-wrap">
				    <a href="#" id="update_address" onclick="update_address()">Update</a>
				    <a href="#" id="cancel_address">Cancel</a>
				</div>
			</div>
			</form>
        </div>
        
         <div class="right-column-wrap-birth">
            <div class="header">
                <h2>Birth Settings</h2>
            </div>
            <form action="updateBirth" name="updateBirthForm" id="updateBirthForm">
            <input type="hidden" value="<%= user.getId() %>" name="user_id"/>
            <div class="input-line">
                <div class="input-container">
                    <% if(user != null) {  %>
                    <div class="input-item">
						<select id="gender" name="gender">
                            <option value="Male" <%= user.getBirth().get().getSex().name().equals("MALE") ? "selected" : "" %>>Male</option>
                            <option value="Female" <%= user.getBirth().get().getSex().name().equals("FEMALE") ? "selected" : "" %>>Female</option>
                            <option value="Other" <%= user.getBirth().get().getSex().name().equals("OTHER") ? "selected" : "" %>>Other</option>
                         </select>
						<label>Sex</label>
				    </div>
				    <div class="input-item"></div>
				    <div class="input-item">
				        <span class="icon"><ion-icon name="balloon"></ion-icon></span> 
				         <input type="date" name="date" value="<%= (String) request.getAttribute("formattedDate") %>">
				        <label>Date of Birth</label>
				    </div>
				    <% } %>
                </div>
            </div>
            <div class="btns-area">
			    <div class="update-cancel-wrap">
				    <a href="#" id="update_birth" onclick="update_birth()">Update</a>
				    <a href="#" id="cancel_birth">Cancel</a>
				</div>
			</div>
			</form>
        </div>
        
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
    <!--<script type="text/javascript" src="js/updateUserPage.js"></script> -->
    <script type="text/javascript">
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

    
    const back_button = document.getElementById('go-back-icon');
    back_button.addEventListener('click', () => {
    	window.location.replace('listPage.jsp?windowId=<%= windowId %>');
    });


    /* Buttons */
       const account_button = document.getElementById('account');
       const info_button = document.getElementById('contact');
       const address_button = document.getElementById('address');
       const birth_button = document.getElementById('birth');
       
       /* Wrappers */
       const nothing_chosen = document.querySelector('.nothing-chosen');
       const right_column_wrap_account = document.querySelector('.right-column-wrap-account');
       const right_column_wrap_contact = document.querySelector('.right-column-wrap-contact');
       const right_column_wrap_address = document.querySelector('.right-column-wrap-address');
       const right_column_wrap_birth = document.querySelector('.right-column-wrap-birth');
       
       account_button.addEventListener('click', () => {
       	account_button.classList.add('active');
       	right_column_wrap_account.classList.add('active');
       	removeActiveIdentity(info_button, address_button, birth_button, nothing_chosen, right_column_wrap_contact, right_column_wrap_address, right_column_wrap_birth);
       });

       info_button.addEventListener('click', () => {
       	info_button.classList.add('active');
       	right_column_wrap_contact.classList.add('active');
       	removeActiveIdentity(account_button, address_button, birth_button, nothing_chosen, right_column_wrap_account, right_column_wrap_address, right_column_wrap_birth);
       });

       address_button.addEventListener('click', () => {
       	address_button.classList.add('active');
       	right_column_wrap_address.classList.add('active');
       	removeActiveIdentity(account_button, info_button, birth_button, nothing_chosen, right_column_wrap_account, right_column_wrap_contact, right_column_wrap_birth);
       });
       
       birth_button.addEventListener('click', () => {
       	birth_button.classList.add('active');
       	right_column_wrap_birth.classList.add('active');
       	removeActiveIdentity(account_button, info_button, address_button, nothing_chosen, right_column_wrap_account, right_column_wrap_contact, right_column_wrap_address);
       });
       
       function removeActiveIdentity(button_one, button_two, button_three, wrapper_one, wrapper_two, wrapper_three, wrapper_fore) {
       	if(button_one !== null) {
       		button_one.classList.remove('active');
       	}
       	
       	if(button_two !== null) {
       		button_two.classList.remove('active');
       	}
       	
       	if(button_three !== null) {
       		button_three.classList.remove('active');
       	}
       	
       	if(wrapper_one !== null) {
       		wrapper_one.classList.remove('active');
       	}
       	
           if(wrapper_two !== null) {
       		wrapper_two.classList.remove('active');
       	}
       	
           if(wrapper_three !== null) {
           	wrapper_three.classList.remove('active');
           }
           
           if(wrapper_fore !== null) {
           	wrapper_fore.classList.remove('active');
           }
       };
       
       /* Кнопка сброс */
       document.getElementById('cancel_account').addEventListener('click', (e) => {
   		e.preventDefault();
   		document.getElementById('updateAccountForm').reset();
     	});
       
       document.getElementById('cancel_contact_info').addEventListener('click', (e) => {
      		e.preventDefault();
      		document.getElementById('updateContactForm').reset();
      	});
       
       document.getElementById('cancel_address').addEventListener('click', (e) => {
     		e.preventDefault();
     		document.getElementById('updateAddressForm').reset();
     	});
       
       document.getElementById('cancel_birth').addEventListener('click', (e) => {
    		e.preventDefault();
    		document.getElementById('updateBirthForm').reset();
    	});
       
       const modal_wrapper = document.querySelector('.modal_wrapper');
       const success_wrap = document.querySelector('.success_wrap');
       const error_wrap = document.querySelector('.error_wrap');
       const shadow = document.querySelector('.shadow');
       
       shadow.addEventListener('click', () => {
       	modal_wrapper.classList.remove("active");
       	success_wrap.classList.remove("active");
       	error_wrap.classList.remove("active");
       });
       
       /* Функції для оновлення даних юзера */
       var msg = null;
       /* Функція на оновлення account info */
       
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
        }, 
        error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", jqXhr);
        }
    });   
         }
       
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
       
       function update_address() {
    	   var data = $("#updateAddressForm").serialize();
           
           $.ajax({
        	   
        	   type: "POST",
               url: "updateAddress",
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

       function update_birth() {
    	   var data = $("#updateBirthForm").serialize();
           
           $.ajax({
        	   
        	   type: "POST",
               url: "updateBirth",
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