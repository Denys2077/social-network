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