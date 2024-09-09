document.querySelector('#reply-close').addEventListener('click', (e) => {
    e.preventDefault();
    document.querySelector('#reply-popup').classList.add('dialog-noshow');
})

// 리뷰 등록
document.querySelector('#reply-register').addEventListener('click', (e) => {
    e.preventDefault();
    const formTag = document.querySelector("#reply-upload-form");
    const formData = new FormData(formTag);
    formData.append('travelNo', document.querySelector('input[name="travelNo"]').value);
    ajax({
            url: '/reply/add',
            method: 'post',
            payload: formData
        },
        (response) => {
            console.log('response', response);
            alert("댓글 등록이 완료되었습니다.");
            location.href=`/travel/detail/${travelNo}`;
        },
        (error) => {
            console.log('error',error)}
    );
});