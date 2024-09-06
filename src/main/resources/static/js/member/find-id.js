const findIdBtnClick = () => {
    const formTag = document.querySelector("#find-id-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    if (!nameCheck()) {
        // alert('이름 오류');
        elName.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    if (!birthdateCheck()) {
        // alert('생일 오류');
        elBirthdate.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }

    ajax({
            url: '/find-id',
            method: 'post',
            payload: formProps,
            blockValidateForm: true
        },
        (response) => {
            // 찾은 아이디를 표시

            viewFindId.classList.remove('hide');
            document.querySelector(".view-find-id span").textContent = "아이디 : " + response.memberId;
            findIdBtn.classList.add('hide');
            loginBtn.classList.remove(('hide'))
        }, (error) => {
        console.log(error);
        if(error.code === 'M003') {
            birthdateSuccessMessage.classList.remove('hide');
            birthdateSuccessMessage.classList.add('hide');
            birthdateFailureMessage.classList.remove('hide');
            // birthdateFailureMessage.classList.add('hide');
            nameSuccessMessage.classList.remove('hide');
            nameSuccessMessage.classList.add('hide');
            nameFailureMessage.classList.remove('hide');
            nameFailureMessage.innerHTML = '유효하지 않은 회원정보입니다.';
            birthdateFailureMessage.innerHTML = '유효하지 않은 회원정보입니다.';

            elName.classList.remove('border-red');
            elName.classList.add('border-red');
            elBirthdate.classList.remove('border-red');
            elBirthdate.classList.add('border-red');
        }
        });
}
// 찾은 아이디 정보 가져오기
const viewFindId = document.querySelector('#view-find-id');
const findIdBtn = document.querySelector('#find-id-btn');
const loginBtn = document.querySelector('#login-Btn');

// 생년월일 정보 가져오기
const elBirthdate = document.querySelector('#birthDate');
const birthdateSuccessMessage = document.querySelector('#birthdate-success-message');
const birthdateFailureMessage = document.querySelector('#birthdate-failure-message');
// 이름 가져오기
const elName = document.querySelector('#name');
const nameSuccessMessage = document.querySelector('#name-success-message');
const nameFailureMessage = document.querySelector('#name-failure-message');


elBirthdate.onkeyup = function () {
    birthdateCheck();
}
elName.onkeyup = function () {
    nameCheck();
}

// form 조건식 정의 시작

function validBirthdate(str) {
    return /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/.test(str);
}

// form 조건식 정의 종료

function birthdateCheck() {
    if (!validBirthdate(elBirthdate.value)) {
        birthdateFailureMessage.innerHTML = '생년월일이 유효하지 않습니다.';
        birthdateSuccessMessage.classList.add('hide');
        birthdateFailureMessage.classList.remove('hide');
        elBirthdate.classList.add('border-red');
        return false;
    }
    birthdateSuccessMessage.innerHTML = '생년월일이 유효합니다.';
    birthdateSuccessMessage.classList.remove('hide');
    birthdateFailureMessage.classList.add('hide');
    elBirthdate.classList.remove('border-red');
    return true;
}

function nameCheck() {
    if (elName.value.length === 0) {
        nameFailureMessage.innerHTML = '이름이 유효하지 않습니다.';
        nameSuccessMessage.classList.add('hide');
        nameFailureMessage.classList.remove('hide');
        elName.classList.add('border-red');
        return false;
    }
    nameSuccessMessage.innerHTML = '이름이 유효합니다.';
    nameSuccessMessage.classList.remove('hide');
    nameFailureMessage.classList.add('hide');
    elName.classList.remove('border-red');
    return true;
}

