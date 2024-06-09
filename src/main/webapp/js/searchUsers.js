/**
 * 
 */
const wrapper = document.querySelector('.wrapper');
const contacts_link = document.getElementById('contacts-link');
const users_link = document.getElementById('users-link');

contacts_link.addEventListener('click', () => {
	wrapper.classList.add('active');
});

users_link.addEventListener('click', () => {
	wrapper.classList.remove('active');
});

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

document.querySelectorAll('.user-info-users').forEach(user => {
	contact.addEventListener('click', (event) => {
		stopLoadingContacts();
		stopLoadingUsers();

		const card_id = user.getAttribute('id');
		const bd_userId = user.getAttribute('data-user-id');
		const photo = document.getElementsByTagName('img')[card_id].getAttribute('src');
		const fullname = document.getElementsByClassName('fullname')[card_id].textContent;

		const [firstNameElement, lastNameElement] = fullname.split(' ');

		var contact_photo = document.getElementById('contact_photo');
		contact_photo.setAttribute('src', photo);

		var first_name_contact = document.getElementById('first_name_contact');
		first_name_contact.innerHTML = firstNameElement;

		var last_name_contact = document.getElementById('last_name_contact');
		last_name_contact.innerHTML = lastNameElement;

		card_dont_contact.classList.add('active');
		contact_wrap.classList.add('active');


	});
});

setInterval(500, loadGroups);
    
    function loadGroups() {
    	$.post("loadgroups",{}, function(data, status){
    		document.getElementsByClassName("group-list")[0].innerHTML = data;
    		console.log(data);
    	});
    }
 
    const group_wrap_out = document.querySelector('.group-wrap-out');
    const group_wrap_shadow = document.querySelector('.group-wrap-shadow');
    const group_wrap_window = document.querySelector('.group-wrap-window');
    
    group_wrap_shadow.addEventListener('click', ()=> {
    	group_wrap_out.classList.remove('active');
    });
    
    function addToGroupChat() {
    	const parts = referenceCreateChat.split(',');

    	const friend_id_value = parts[0].split('=')[1];
    	const current_id_value = parts[1].split('=')[1];
    	
    	console.log("add to group chat user with id = " + friend_id_value);
    	
    	setTimeout(() => {
    		group_wrap_out.classList.add('active');
		}, 100);
    	
    }