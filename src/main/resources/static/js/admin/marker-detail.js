function markerModify(isAccepted) {
    const formTag = document.querySelector("#detail-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    formProps.isAccepted = isAccepted;
    if(!isAccepted) {
        formProps.latitude = 1;
        formProps.longitude = 1;
    }
    console.log(formProps);
    ajax({
        url: '/admin/marker',
        method: 'post',
        payload: formProps
    },
        (response) => {
            // 현재 URL에서 쿼리 문자열을 추출
            const urlParams = new URLSearchParams(window.location.search);

            // 'currentPage' 매개변수의 값을 가져오기
            const currentPage = urlParams.get('currentPage');
            location.href = '/admin/marker?currentPage=' + currentPage;
        },
        (error) => {
        alert("입력값 입력 중에 예외가 발생했습니다.");
        console.log('error',error)}
    );
}

function goBack() {
    // 현재 URL에서 쿼리 문자열을 추출
    const urlParams = new URLSearchParams(window.location.search);

    // 'currentPage' 매개변수의 값을 가져오기
    const currentPage = urlParams.get('currentPage');
    location.href = '/admin/marker?currentPage=' + currentPage;
}