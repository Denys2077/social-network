/**
 * 
 */
 const outer_list_user_wrap = document.querySelector('.outer-list-user-wrap');
    const shadow_list_user_wrap = document.querySelector('.shadow-list-user-wrap');
    
    outer_list_user_wrap.addEventListener('click', ()=> {
    	outer_list_user_wrap.classList.add('active');
    });
    
    
    //-----------------------------------------------------------------------------
    const outer_option_user_wrap = document.querySelector('.outer-option-user-wrap');
    const option_user = document.querySelector('.option-user');

    outer_option_user_wrap.addEventListener('click', () => {
        outer_option_user_wrap.classList.remove('active');
    });

    function toggleOptionUser(user_id) {
        outer_option_user_wrap.classList.add('active');

        // Додаємо id та onclick до тегів <a>
        const links = option_user.querySelectorAll('a');
        links.forEach((link, index) => {
            link.id = `link-${user_id}-${index + 1}`;

            if (index === 0) {
                link.onclick = () => deleteFromGroupChat(user_id);
            } else if (index === 1) {
                link.onclick = () => createPrivateChat(user_id);
            } else if (index === 2) {
                link.onclick = () => addUserToContacts(user_id);
            }
        });

        console.log(user_id);
    }

    function deleteFromGroupChat(user_id) {
        console.log('Delete from group chat:', user_id);
    }

    function createPrivateChat(user_id) {
        console.log('Create private chat:', user_id);
    }

    function addUserToContacts(user_id) {
        console.log('Add user to contacts:', user_id);
    }