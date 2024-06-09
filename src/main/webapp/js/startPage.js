/**
 * Creating "Login" wrapper
 */
const wrapperLogin = document.querySelector('.wrapper');
const buttonLogin = document.querySelector('.btnLogin');
const iconCloseLogin = document.querySelector('.icon-close');

buttonLogin.addEventListener('click', ()=> {
	wrapperLogin.classList.add('active-btnLogin');
});

iconCloseLogin.addEventListener('click', ()=> {
	wrapperLogin.classList.remove('active-btnLogin');
});

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
