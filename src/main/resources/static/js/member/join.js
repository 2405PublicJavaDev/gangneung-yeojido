const joinBtnClick = () => {
    const formTag = document.querySelector("#join-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    ajax({
            url: '/join',
            method: 'post',
            payload: formProps
        },
        (response) => {
            location.href = "/login";
        },
        (error) => {
            alert('로그인 에러 발생함');
            console.log('error',error);
    }
    );

}