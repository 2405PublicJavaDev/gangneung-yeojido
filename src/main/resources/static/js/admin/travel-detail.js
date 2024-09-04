function travelModify() {
    const formTag = document.querySelector("#detail-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    console.log(formProps);
    if(confirm("정보 수정을 완료하시겠습니까?")) {
        ajax({
                url: '/admin/travel',
                method: 'post',
                payload: formProps
            },
            (response) => {

                // 현재 URL에서 쿼리 문자열을 추출
                const urlParams = new URLSearchParams(window.location.search);

                // 'currentPage' 매개변수의 값을 가져오기
                const currentPage = urlParams.get('currentPage');
                location.href = '/admin/travel?currentPage=' + currentPage;
                alert("수정이 완료되었습니다.");
            },
            (error) => {
                console.log('error', error)
            }
        );
    }
}

function travelRemove(travelNo) {
    if(confirm("정말 여행지를 삭제하시겠습니까?")) {
        ajax({
                // 백틱(`)을 사용하여 문자열을 감싸면 문자열 내에서 변수 삽입과 여러 줄 문자열을 작성
                url: `/admin/travel/${travelNo}`,
                method: 'delete',
            },
            (response) => {
                console.log(response);

                // 현재 URL에서 쿼리 문자열을 추출
                const urlParams = new URLSearchParams(window.location.search);

                // 'currentPage' 매개변수의 값을 가져오기
                const currentPage = urlParams.get('currentPage');
                location.href = '/admin/travel?currentPage=' + currentPage;
                alert("삭제가 완료되었습니다.");
            },
            (error) => {
                console.log('error', error)
            }
        );
    }
}

function goBack() {
    if(confirm("정보 수정을 취소하시겠습니까?")) {
        const urlParams = new URLSearchParams(window.location.search);
        const currentPage = urlParams.get("currentPage");
        location.href = "/admin/travel?currentPage=" + currentPage;
    }
}