/**
 * 
 */
document.querySelectorAll('chat-card').forEach(card => {
	card.addEventListener('click', (event) => {
		const card_id = card.getAttribute('id');
		console.log(card_id);
	});
});

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
	home_button.classList.remove('active');
});
