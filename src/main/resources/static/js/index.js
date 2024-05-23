const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');
const registerLink = document.querySelector('.register-link');
const resetPasswordLink = document.querySelector('.reset-link');

loginLink.addEventListener('click', () => {
    wrapper.classList.remove('login-active');
    wrapper.classList.remove('register-active');
});

registerLink.addEventListener('click', () => {
    wrapper.classList.remove('reset-active');
    wrapper.classList.add('login-active');
    wrapper.classList.add('register-active');
});

resetPasswordLink.addEventListener('click', () => {
    wrapper.classList.remove('register-active');
    wrapper.classList.add('login-active');
    wrapper.classList.add('reset-active');
})

function login(event) {
    event.preventDefault();
    let userEmail = document.getElementById('login-email');
    let userPassword = document.getElementById('login-password');
    let validUser = false;
    const apiUrl = "http://localhost:8080/login";
    const data = {
        email: userEmail.value,
        password: userPassword.value
    };
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    };
    fetch(apiUrl, requestOptions)
    .then(res => {
        if (res.status != 401 & res.status != 200) {
           throw new Error("Error occured!");
        }
        return res.json()
    })
    .then(data => {
        const result = data.key;
        debugger;
        if (result == "User Login Successful") {
            alert("Login Successful!")
            validUser = true;
        } else {
            alert(result);
        }
    })
    .catch(error => {
        console.log("Error:", error);
    })
}