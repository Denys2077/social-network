/**
 * 
 */
const wrapper = document.getElementById('manager-action');
const button = document.getElementById('usersManager');
const iconClose = document.querySelector('.close-icon');

button.addEventListener('click', ()=> {
	wrapper.classList.add('active-button-manager');
});
/*
iconClose.addEventListener('click', ()=> {
	wrapper.classList.remove('active-button-manager');
});*/

const buttonAddUsers = document.getElementById('addUsers');
buttonAddUsers.addEventListener('click', () => {
	window.location.href = "registration.jsp";
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