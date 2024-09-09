const findPwBtnClick = () => {
    const formTag = document.querySelector("#find-pw-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    if (!idCheck()) {
        // alert('아이디 오류입니다.');
        elInputMemberId.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    if (!emailCheck()) {
        // alert('이메일 오류');
        elInputEmail.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    ajax({
            url: '/find-pw',
            method: 'post',
            payload: formProps,'email': elInputEmail.value,
            blockValidateForm: true
        },
        (response) => {
            alert('임시 비밀번호가 이메일로 전송되었습니다.');
            location.href = "/login";
        },
        (error)=>{
            console.error(error);
            if(error.code === 'M003' || error.code === 'C001'){
                idSuccessMessage.classList.remove('hide');
                idSuccessMessage.classList.add('hide');
                idFailureMessage.classList.remove('hide');
                // birthdateFailureMessage.classList.add('hide');
                emailSuccessMessage.classList.remove('hide');
                emailSuccessMessage.classList.add('hide');
                emailFailureMessage.classList.remove('hide');
                emailFailureMessage.innerHTML = '유효하지 않은 회원정보입니다.';
                idFailureMessage.innerHTML = '유효하지 않은 회원정보입니다.';

                elInputMemberId.classList.remove('border-red');
                elInputMemberId.classList.add('border-red');
                elInputEmail.classList.remove('border-red');
                elInputEmail.classList.add('border-red');
            }
        }
    );

}
// 아이디 입력창 정보 가져오기
const elInputMemberId = document.querySelector('#memberId');
const idSuccessMessage = document.querySelector("#id-success-message")
const idFailureMessage = document.querySelector("#id-failure-message");
// 이메일 정보 가져오기
const elInputEmail = document.querySelector('#email');
const emailSuccessMessage = document.querySelector('#email-success-message');
const emailFailureMessage = document.querySelector('#email-failure-message');

elInputMemberId.onkeyup = function () {
    idCheck();
}

elInputEmail.onkeyup = function () {
    emailCheck();
}

// form 조건식 정의 시작
function idLength(value) {
    return value.length >= 5 && value.length <= 20
}

function onlyNumberAndEnglish(str) {
    return /^[A-Za-z0-9]{5,20}$/.test(str);
}

function validEmail(str) {
    return /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(str);
}

// form 조건식 정의 종료
function idCheck() {
    if (idLength(elInputMemberId.value) === false) {
        idFailureMessage.innerHTML = '아이디는 5~20글자이어야 합니다.';
        idSuccessMessage.classList.add('hide');
        idFailureMessage.classList.remove('hide');
        elInputMemberId.classList.add('border-red');
        return false;
    } else if (!onlyNumberAndEnglish(elInputMemberId.value)) {
        idFailureMessage.innerHTML = '영어 또는 숫자만 가능합니다.';
        idSuccessMessage.classList.add('hide');
        idFailureMessage.classList.remove('hide');
        elInputMemberId.classList.add('border-red');
        return false;
    }
    idFailureMessage.classList.add('hide');
    elInputMemberId.classList.remove('border-red');
    idSuccessMessage.innerHTML = '사용할 수 있는 아이디입니다.';
    idSuccessMessage.classList.remove('hide');
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



// document.querySelector('#find-pw-btn').addEventListener('click', (e) => {
//     ajax({
//         url: '/find-pw',
//         method: 'post',
//         payload: {
//             'email': elInputEmail.value
//         },
//         blockValidateForm: true,
//     }, (response) => {
//         console.log(response);
//         alert('이메일 보냄');
//     }, (error) => {
//         console.log(error);
//         alert('이메일 보냄 오류')
//     })
// });

