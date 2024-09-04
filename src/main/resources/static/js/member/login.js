const loginBtnClick = () => {
    const formTag = document.querySelector("#login-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    ajax({
            url: '/login',
            method: 'post',
            payload: formProps
        },
        (response) => {
            location.href = "/";
        },
        (error) => {
            alert('로그인 에러 발생함');
            console.log('error',error);
    }
    );

}