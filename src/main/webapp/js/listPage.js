/**
 * 
 */
const go_back = document.querySelector('.icon-back');

go_back.addEventListener('click', () => {
	window.location.replace('mainPage.jsp');
});

 const searchUsers = () => {
        const list_users = document.getElementById('list-users');
        const user = list_users.querySelectorAll('.card');
       // const user_name = list_users.getElementsByTagName("h2");
        const search_line = document.getElementById("search-input").value.toUpperCase();
        const search_param = document.getElementById("search-by").value;
        
        const sorting_by = document.getElementById('sorting-by').value;
        
        var array = Array.from(user);
        console.log(array);
        
        for (var i = 0; i < user.length; i++) {
            let match;
            if (search_param === "name") {
                match = user[i].getElementsByTagName('h2')[0];
            } else if (search_param === "department") {
                match = user[i].getElementsByTagName('p')[0];
            } else if (search_param === "position") {
                match = user[i].getElementsByTagName('p')[1];
            }

            if (match) {
                let textValue = match.textContent || match.innerHTML;
                if (search_param !== "name") {
                    textValue = textValue.split(":")[1].trim();
                }
                if (textValue.toUpperCase().indexOf(search_line) > -1) {
                    user[i].style.display = "";
                } else {
                    user[i].style.display = "none";
                }
            }
        }
    };
    
const buttonUpdate = document.getElementById('update-user');
buttonUpdate.addEventListener('click', () => {
	upd_del_user_wrap.classList.remove('active');
    user_wrap.classList.remove('active');
    setTimeout(() => {
    	window.location.href = "updateUserPage.jsp";
	}, 1000);
});
