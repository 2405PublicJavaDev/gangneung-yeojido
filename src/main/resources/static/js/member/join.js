const joinBtnClick = () => {
    const formTag = document.querySelector("#join-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    if(!idCheck()) {
        alert('아이디 오류입니다.');
        elInputMemberId.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    if(!pwCheck()) {
        alert('비밀번호 오류입니다.')
        elInputPassword.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    if(!pwCheckCheck()) {
        alert('비밀번호 확인 오류입니다.')
        elInputConfirmPassword.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }

    alert("모든 조건을 만족합니다.");
    ajax({
            url: '/join',
            method: 'post',
            payload: formProps,
            blockValidateForm: true
        },
        (response) => {
            location.href = "/login";
        },
        (error) => {
            alert('회원가입 에러 발생함');
            console.log('error',error);
    }
    );

}
// 아이디 입력창 정보 가져오기
const elInputMemberId = document.querySelector('#memberId');
const idSuccessMessage = document.querySelector("#id-success-message")
const idFailureMessage = document.querySelector("#id-failure-message");
// 비밀번호 입력창 정보 가져오기
const elInputPassword = document.querySelector('#password');
const pwFailureMessage = document.querySelector('#pw-failure-message');
// 비밀번호 확인 입력창 정보 가져오기
const elInputConfirmPassword = document.querySelector('#confirmPassword');
const pwCheckFailureMessage = document.querySelector('#pw-check-failure-message');

elInputMemberId.onkeyup = function () {
    // 값을 입력한 경우
    idCheck();
}

elInputPassword.onkeyup = function () {
    // 값을 입력한 경우
    pwCheck();
};

elInputConfirmPassword.onkeyup = function () {
    // 값을 입력한 경우
    pwCheckCheck();
};
// form 조건식 정의 시작
function idLength(value) {
    return value.length >= 5 && value.length <= 20
}

function onlyNumberAndEnglish(str) {
    return /^[A-Za-z0-9][A-Za-z0-9]{5,20}$/.test(str);
}

function strongPassword (str) {
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"])[A-Za-z\d\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]{8,16}$/.test(str);
}

function isMatch (password1, password2) {
    return password1 === password2;
}
// form 조건식 정의 종료
function idCheck() {
    if(idLength(elInputMemberId.value) === false) {
        idFailureMessage.innerHTML = '아이디는 5~20글자이어야 합니다.';
        idSuccessMessage.classList.add('hide');
        idFailureMessage.classList.remove('hide');
        return false;
    }
    else if(!onlyNumberAndEnglish(elInputMemberId.value)) {
        idFailureMessage.innerHTML = '영어 또는 숫자만 가능합니다.';
        idSuccessMessage.classList.add('hide');
        idFailureMessage.classList.remove('hide');
        return false;
    }
    idFailureMessage.classList.add('hide');
    idSuccessMessage.innerHTML = '사용할 수 있는 아이디입니다.';
    idSuccessMessage.classList.remove('hide');
    return true;
}

function pwCheck() {
    if(!strongPassword(elInputPassword.value)) {
        pwFailureMessage.innerHTML = '8~16글자, 영문(대/소문자), 숫자, 특수문자를 사용하세요.';
        pwFailureMessage.classList.remove('hide');
        return false;
    }
    pwFailureMessage.classList.add('hide');
    return true;
}

function pwCheckCheck() {
    if (!isMatch(elInputPassword.value, elInputConfirmPassword.value)) {
        pwCheckFailureMessage.innerHTML = '비밀번호가 일치하지 않습니다.';
        pwCheckFailureMessage.classList.remove('hide');
        return false;
    }
    pwCheckFailureMessage.classList.add('hide');
    return true;
}