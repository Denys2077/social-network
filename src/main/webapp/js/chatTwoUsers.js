/**
 * 
 */
/*const wrapper = document.querySelector('.user-info-wrap');
const shadow = document.querySelector('.shadow');
const user_wrap = document.querySelector('.user-wrap');

shadow.addEventListener('click', () => {
	wrapper.classList.remove('active');
	user_wrap.classList.remove('active');
});

const header_user = document.getElementById('header-user');
header_user.addEventListener('click', () => {
	wrapper.classList.add('active');
	user_wrap.classList.add('active');
});

const close_button = document.querySelector('.icon-close');
close_button.addEventListener('click', () => {
	wrapper.classList.remove('active');
	user_wrap.classList.remove('active');
});
*/
const imgFile = document.getElementById('img-file');
const inputFile = document.getElementById('input-file');
const modalWrapper = document.getElementById('modal-wrapper');
const txtPreview = document.getElementById('txt-preview');
const txtName = document.getElementById('txt-name');
const modalTitle = document.getElementById('modal-title');
const shadow_elem = document.getElementById('shadow');
const closeButton = document.getElementById('close-icon');

inputFile.onchange = function() {
	const file = inputFile.files[0];
	if (file) {
		const fileType = file.type;

		if (fileType.startsWith('image/')) {
			imgFile.src = URL.createObjectURL(file);
			imgFile.style.display = 'block';
			txtPreview.style.display = 'none';
			modalTitle.textContent = 'Send Image';
		} else if (fileType === 'text/plain') {
			txtName.textContent = file.name;
			imgFile.style.display = 'none';
			txtPreview.style.display = 'block';
			modalTitle.textContent = 'Send Text File';
		}

		modalWrapper.classList.add('active');
	}
};

shadow_elem.addEventListener('click', () => {
	modalWrapper.classList.remove('active');
	modalTitle.textContent = '';
	inputFile.value = "";
	imgFile.src = "";
	txtName.textContent = "";
});

closeButton.addEventListener('click', () => {
	modalWrapper.classList.remove('active');
	modalTitle.textContent = '';
	inputFile.value = "";
	imgFile.src = "";
	txtName.textContent = "";
});

document.querySelector('.blob-window').addEventListener('click', (event) => {
	event.stopPropagation();
});