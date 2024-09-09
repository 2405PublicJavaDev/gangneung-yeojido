const modifyBtnClick = () => {
    const formTag = document.querySelector("#modify-member-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    if (!pwCheck()) {
        // alert('비밀번호 오류입니다.')
        elInputPassword.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    if (!pwCheckCheck()) {
        // alert('비밀번호 확인 오류입니다.')
        elInputConfirmPassword.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    // if (!emailCheck()) {
    //     // alert('이메일 오류');
    //     elInputEmail.scrollIntoView({behavior: 'smooth', block: 'start'});
    //     return;
    // }
    if (!phoneCheck()) {
        // alert('전화번호 오류');
        elPhone.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }

    ajax({
            url: '/modify-member',
            method: 'post',
            payload: formProps,
            blockValidateForm: true
        },
        (response) => {
            location.href = "modify-member";
        }
        // ,
        // (error) => {
        //     if (error.code === 'C003') {
        //         alert('아이디 혹은 이메일이 중복되었습니다.');
        //     } else if (error.code === 'M002') {
        //         alert('이메일 인증 실패')
        //     }
        //     console.log('error', error);
        // }
    );

}
// 비밀번호 입력창 정보 가져오기
const elInputPassword = document.querySelector('#password');
const pwSuccessMessage = document.querySelector('#pw-success-message');
const pwFailureMessage = document.querySelector('#pw-failure-message');
// 비밀번호 확인 입력창 정보 가져오기
const elInputConfirmPassword = document.querySelector('#confirmPassword');
const pwCheckSuccessMessage = document.querySelector('#pw-check-success-message');
const pwCheckFailureMessage = document.querySelector('#pw-check-failure-message');
// 이메일 정보 가져오기
const elInputEmail = document.querySelector('#email');
const emailSuccessMessage = document.querySelector('#email-success-message');
const emailFailureMessage = document.querySelector('#email-failure-message');
// 전화번호 정보 가져오기
const elPhone = document.querySelector('#phone');
const phoneSuccessMessage = document.querySelector('#phone-success-message');
const phoneFailureMessage = document.querySelector('#phone-failure-message');

elInputPassword.onkeyup = function () {
    pwCheck();
};

elInputConfirmPassword.onkeyup = function () {
    pwCheckCheck();
};
elInputEmail.onkeyup = function () {
    emailCheck();
}
elPhone.onkeyup = function () {
    phoneCheck();
}

// form 조건식 정의 시작
function strongPassword(str) {
    return /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(str);
}

function validEmail(str) {
    return /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(str);
}

function isMatch(password1, password2) {
    return password1 === password2;
}

function validPhone(str) {
    return /^\d{10,11}$/.test(str);
}

// form 조건식 정의 종료


function pwCheck() {
    if (!strongPassword(elInputPassword.value)) {
        pwFailureMessage.innerHTML = '8~16글자의 영문(대/소문자), 숫자, 특수문자( !@#$%^*+=- )를 사용하세요.';
        pwSuccessMessage.classList.add('hide');
        pwFailureMessage.classList.remove('hide');
        elInputPassword.classList.add('border-red');
        return false;
    }
    pwSuccessMessage.innerHTML = '유효한 비밀번호 입니다.'
    pwSuccessMessage.classList.remove('hide');
    pwFailureMessage.classList.add('hide');
    elInputPassword.classList.remove('border-red');
    return true;
}

function pwCheckCheck() {
    if (!isMatch(elInputPassword.value, elInputConfirmPassword.value)) {
        pwCheckFailureMessage.innerHTML = '비밀번호가 일치하지 않습니다.';
        pwCheckSuccessMessage.classList.add('hide');
        pwCheckFailureMessage.classList.remove('hide');
        elInputConfirmPassword.classList.add('border-red');
        return false;
    }
    pwCheckSuccessMessage.innerHTML = '비밀번호와 일치합니다.';
    pwCheckSuccessMessage.classList.remove('hide');
    pwCheckFailureMessage.classList.add('hide');
    elInputConfirmPassword.classList.remove('border-red');
    return true;
}

function emailCheck() {
    if (!validEmail(elInputEmail.value)) {
        emailFailureMessage.innerHTML = '이메일이 유효하지 않습니다.';
        emailSuccessMessage.classList.add('hide');
        emailFailureMessage.classList.remove('hide');
        elInputEmail.classList.add('border-red');
        return false;
    }
    emailSuccessMessage.classList.remove('hide');
    emailSuccessMessage.innerHTML = '유효한 이메일 주소입니다.'
    emailFailureMessage.classList.add('hide');
    elInputEmail.classList.remove('border-red');
    return true;
}


function phoneCheck() {
    if (!validPhone(elPhone.value)) {
        phoneFailureMessage.innerHTML = '전화번호가 유효하지 않습니다.';
        phoneSuccessMessage.classList.add('hide');
        phoneFailureMessage.classList.remove('hide');
        elPhone.classList.add('border-red');
        return false;
    }
    phoneSuccessMessage.innerHTML = '전화번호가 유효합니다.';
    phoneSuccessMessage.classList.remove('hide');
    phoneFailureMessage.classList.add('hide');
    elPhone.classList.remove('border-red');
    return true;
}

document.querySelector('#email-btn').addEventListener('click', (e) => {
    ajax({
        url: '/send-check-code',
        method: 'post',
        payload: {
            'email': elInputEmail.value
        },
        blockValidateForm: true,
    }, (response) => {
        console.log(response);
        alert('이메일 보냄');
    }, (error) => {
        console.log(error);
        alert('이메일 보냄 오류')
    })
});
document.querySelector('#auth-btn').addEventListener('click', (e) => {
    ajax({
        url: '/valid-check-code',
        method: 'post',
        payload: {
            'email': elInputEmail.value,
            'auth': document.querySelector('#auth').value
        },
        blockValidateForm: true,
    }, (response) => {
        console.log(response);
        alert('이메일 검증 성공');
    }, (error) => {
        console.log(error);
        alert('이메일 검증 실패');
    })
})

// 패스워드 아이콘 클릭
const pwIcon = document.querySelector('.pwIcon');
const pwInput = document.querySelector('#password');

pwIcon.addEventListener('click', function () {
    const type = pwInput.getAttribute('type') === 'password' ? 'text' : 'password';
    pwInput.setAttribute('type', type);

    if (type === 'password') {
        this.classList.remove('fi-rr-eye-crossed');
        this.classList.add('fi-rs-eye');
    } else {
        this.classList.remove('fi-rs-eye');
        this.classList.add('fi-rr-eye-crossed');
    }
});

// 패스워드 확인 아이콘 클릭
const cpwIcon = document.querySelector('.cpwIcon');
const cpwInput = document.querySelector('#confirmPassword');

cpwIcon.addEventListener('click', function () {
    const type = cpwInput.getAttribute('type') === 'password' ? 'text' : 'password';
    cpwInput.setAttribute('type', type);

    if (type === 'password') {
        this.classList.remove('fi-rr-eye-crossed');
        this.classList.add('fi-rs-eye');
    } else {
        this.classList.remove('fi-rs-eye');
        this.classList.add('fi-rr-eye-crossed');
    }
});



