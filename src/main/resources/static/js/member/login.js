const loginBtnClick = () => {
    const formTag = document.querySelector("#login-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    ajax({
            url: '/login',
            method: 'post',
            payload: formProps,
            blockValidateForm: true
        },
        (response) => {
            location.href = "/";
        },
        (error) => {
            // alert('로그인 에러 발생함');
            console.log('error',error);
            idPwCheck()
            loginContainer.scrollIntoView({behavior: 'smooth', block: 'start'});

    }
    );

}
const loginContainer = document.querySelector('#login-container')
// 아이디 입력창 정보 가져오기
const elInputMemberId = document.querySelector('#memberId');
const idFailureMessage = document.querySelector("#id-failure-message");
// 비밀번호 입력창 정보 가져오기
const elInputPassword = document.querySelector('#password');

elInputMemberId.onkeyup = function () {
    elInputMemberId.classList.remove('border-red');
    elInputPassword.classList.remove('border-red');
}

elInputPassword.onkeyup = function () {
    elInputMemberId.classList.remove('border-red');
    elInputPassword.classList.remove('border-red');
};

// form 조건식 정의 시작
function idLength(value) {
    return value.length >= 5 && value.length <= 20
}

function onlyNumberAndEnglish(str) {
    return /^[A-Za-z0-9]{5,20}$/.test(str);
}

function strongPassword(str) {
    return /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(str);
}

// form 조건식 정의 종료

function idPwCheck() {
    if (idLength(elInputMemberId.value) === false || !onlyNumberAndEnglish(elInputMemberId.value) || !strongPassword(elInputPassword.value)) {
        idFailureMessage.innerHTML = '아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.';
        idFailureMessage.classList.remove('hide');
        elInputMemberId.classList.add('border-red');
        elInputPassword.classList.add('border-red');
        return false;
    }
    idFailureMessage.classList.add('hide');
    elInputMemberId.classList.remove('border-red');
    elInputPassword.classList.remove('border-red');
    return true;
}

// function pwCheck() {
//     if (!strongPassword(elInputPassword.value)) {
//         pwFailureMessage.innerHTML = '8~16글자의 영문(대/소문자), 숫자, 특수문자( !@#$%^*+=- )를 사용하세요.';
//         pwFailureMessage.classList.remove('hide');
//         elInputPassword.classList.add('border-red');
//         return false;
//     }
//     pwFailureMessage.classList.add('hide');
//     elInputPassword.classList.remove('border-red');
//     return true;
// }