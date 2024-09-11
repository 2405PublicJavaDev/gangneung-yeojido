const removeContainer = document.querySelector('.remove-member-container');
// 아이디 입력창 정보 가져오기
const elInputMemberId = document.querySelector('#memberId');
const idFailureMessage = document.querySelector("#id-failure-message");
// 비밀번호 입력창 정보 가져오기
const elInputPassword = document.querySelector('#password');
const removeBtnClick = () => {
    if(!idPwCheck()) {
        return;
    }
    if(confirm('정말로 회원탈퇴 하시겠습니까?')) {
        const formTag = document.querySelector("#remove-member-form");
        const formData = new FormData(formTag);
        const formProps = Object.fromEntries(formData);
        ajax({
                url: '/remove-member',
                method: 'post',
                payload: formProps,
                blockValidateForm: true,
            },
            (response) => {
                alert('회원탈퇴 하였습니다.')
                location.href = "/";
            },
            (error) => {
                console.log('error', error);
                idFailureMessage.innerHTML = '비밀번호가 잘못 되었습니다. 비밀번호를 정확히 입력해 주세요.';
                idFailureMessage.classList.remove('hide');
                elInputMemberId.classList.add('border-red');
                elInputPassword.classList.add('border-red');
            }
        );
    }
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
        idFailureMessage.innerHTML = '비밀번호가 잘못 되었습니다. 비밀번호를 정확히 입력해 주세요.';
        idFailureMessage.classList.remove('hide');
        elInputMemberId.classList.add('border-red');
        elInputPassword.classList.add('border-red');
        return false;
    }
    idFailureMessage.classList.add('hide');
    elInputMemberId.classList.remove('border-red');
    elInputPassword.classList.remove('border-red');
    idFailureMessage.innerHTML = '';
    return true;
}