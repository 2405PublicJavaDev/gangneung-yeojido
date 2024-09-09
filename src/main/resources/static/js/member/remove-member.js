const removeBtnClick = () => {
    const formTag = document.querySelector("#remove-member-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    ajax({
            url: '/remove-member',
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