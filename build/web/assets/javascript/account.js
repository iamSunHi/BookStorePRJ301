function changeAccountTab(item) {
    const option = item.innerText;
    const personalInfo = document.querySelector('.account-info__personal-info');
    const loginInfo = document.querySelector('.account-info__login-info');
    const settingInfo = document.querySelector('.account-info__setting');

    const listInfo = document.querySelectorAll('.account-sidebar__list-item');
    listInfo.forEach(item => {
        if (item.classList.contains('active')) {
            item.classList.remove('active');
            return;
        }
    })
    item.classList.add('active');

    switch (option) {
        case 'Personal Information': {
            loginInfo.classList.remove('active');
            settingInfo.classList.remove('active');
            personalInfo.classList.add('active');
            break;
        }
        case 'Login & Password': {
            personalInfo.classList.remove('active');
            settingInfo.classList.remove('active');
            loginInfo.classList.add('active');
            break;
        }
        case 'Settings':
            personalInfo.classList.remove('active');
            loginInfo.classList.remove('active');
            settingInfo.classList.add('active');
            break;
    }
}