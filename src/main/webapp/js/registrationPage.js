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
const shadow = document.querySelector('.shadow');

let formStepsNum = 0;

nextBtns.forEach(btn => {
	btn.addEventListener('click', () => {
		formStepsNum++;
		updateFormSteps();
		updateProgressBar();
	});
});

prevBtns.forEach(btn => {
	btn.addEventListener('click', () => {
		formStepsNum--;
		updateFormSteps();
		updateProgressBar();
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

btn_done.addEventListener('click', () => {
	modal_wrapper.classList.add("active");
});

shadow.addEventListener('click', () => {
	modal_wrapper.classList.remove("active");
});

 function registration() {
     	var data = $("#registrationForm").serialize();
    	$.ajax({
    		
    		type : 'POST',
    		url : 'controller',
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
    				}, 3000);
    				
    			} else if(msg == 3) {
    				modal_wrapper.classList.add("active");
    	    		error_wrap.classList.add("active");
    			}
    		},
    	    error: function(xhr, status, error) {
    	        console.error(xhr.responseText);
    	    }
    	});	
    }