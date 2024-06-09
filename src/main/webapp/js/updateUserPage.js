/**
 * 
 */
const back_button = document.getElementById('go-back-icon');
back_button.addEventListener('click', () => {
	window.location.replace('listPage.jsp');
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
    
    document.getElementById('cancel_account').addEventListener('click', (e) => {
		e.preventDefault();
		document.getElementById('updateAccountForm').reset();
	});
	
	   /* Функції для оновлення даних юзера */
       var msg = null;
       /* Функція на оновлення account info */
	    function update_account() {
		var data = $("#updateAccountForm").serialize();
		$.ajax ({
			
			type : "POST",
			url : "updateAccount",
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
	}z