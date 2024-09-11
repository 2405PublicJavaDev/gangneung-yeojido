// 별점
const starsContainer = document.querySelectorAll('.stars-container');
starsContainer.forEach((starsContainer) => {
    const stars =  starsContainer.querySelectorAll('.fa-star');
    stars.forEach(star => {
        star.addEventListener('click', function() {
            const ratingValue = starsContainer.querySelector('span:last-of-type');
            const value = this.getAttribute('data-value');
            ratingValue.textContent = value + ".0";  // 선택한 별점 표시

            // 모든 별을 다시 회색으로 초기화
            stars.forEach(star => star.classList.remove('star-checked'));

            // 선택한 별까지 금색으로 채움
            stars.forEach((star, index) => {
                if (index < value) {
                    star.classList.add('star-checked');
                }
            });
        });
    });
});

// 리뷰 등록
(function () {
    document.querySelector('#review-add-btn').addEventListener('click', (e) => {
        e.preventDefault();
        const formTag = document.querySelector("#review-add-form");
        const formData = new FormData(formTag);
        // 파일을 FormData에 추가
        inputFileDataHandler['review-add-file-upload'].inputFileData.forEach(file => {
            formData.append('uploadFile', file);
        });
        formData.append('score',  document.querySelector('.stars-container span:last-of-type').innerHTML);
        formData.append('travelNo', document.querySelector('input[name="travelNo"]').value);
        ajax({
                url: `/review/add`,
                method: 'post',
                payload: formData,
                isMultipart: true
            },
            (response) => {
                console.log('response', response);
                alert("리뷰 등록이 완료되었습니다.");
                location.href=`/travel/detail/${travelNo}`;
            },
            (error) => {
                alert("리뷰 등록 중에 오류가 발생했습니다.");
                console.log('error',error)}
        );
    });
    document.querySelector('#review-add-close-btn').addEventListener('click', (e) => {
        e.preventDefault();
        document.querySelector('#review-add-popup').classList.add('dialog-noshow');
    });
})();

// 리뷰 수정
(function() {
    document.querySelector('#review-update-btn').addEventListener('click', (e) => {
        e.preventDefault();
        const formTag = document.querySelector("#review-update-form");
        const formData = new FormData(formTag);
        // 파일을 FormData에 추가
        inputFileDataHandler['review-modify-file-upload'].inputFileData.forEach(file => {
            formData.append('reloadFile', file);
        });
        const reviewContent = document.querySelector('#reply-add-content').value;
        formData.append('score',  document.querySelector('#rating-update-value').innerHTML);
        formData.append('travelNo', document.querySelector('input[name="travelNo"]').value);
        ajax({
                url: `/review/modify`,
                method: 'put',
                payload: formData,
                isMultipart: true
            },
            (response) => {
                console.log('response', response);
                alert("리뷰 수정이 완료되었습니다.");
                location.href=`/travel/detail/${travelNo}`;
            },
            (error) => {
                console.log('error',error)}
        );
    });
    document.querySelector('#review-update-close-btn').addEventListener('click', (e) => {
        e.preventDefault();
        document.querySelector('#review-update-popup').classList.add('dialog-noshow');
    });
})();

// 댓글 등록
document.querySelector('#reply-add-btn').addEventListener('click', (e) => {
    e.preventDefault();
    const formTag = document.querySelector("#reply-add-form");
    const formData = new FormData(formTag);
    formData.append('travelNo', travelNo);
    formData.append("parentReviewNo", parentReviewNo);
    const formProps = Object.fromEntries(formData);
    for (let [key, value] of formData.entries()) {
        console.log(key, value);
    }
    ajax({
            url: `/reply/add`,
            method: 'post',
            payload: formProps
        },
        (response) => {
            console.log('response', response);
            alert("댓글 등록이 완료되었습니다.");
            location.href=`/travel/detail/${travelNo}`;
        },
        (error) => {
            alert("댓글 등록 중에 오류가 발생했습니다.")
            console.log('error',error)
    });
});

// 댓글 수정
document.querySelector('#reply-update-btn').addEventListener('click', (e) => {
    e.preventDefault();
    const formTag = document.querySelector("#reply-update-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    for (let [key, value] of formData.entries()) {
        console.log(key, value);
    }
    ajax({
            url: `/reply/modify`,
            method: 'PUT',
            payload: formProps
        },
        (response) => {
            console.log('response', response);
            alert("댓글 수정이 완료되었습니다.");
            location.href=`/travel/detail/${travelNo}`;
        },
        (error) => {
            console.log('error',error)
        });
});

// 리뷰 수정 팝업창 닫기
document.querySelector('#review-update-close-btn').addEventListener('click', (e) => {
    e.preventDefault();
    document.querySelector('#review-update-popup').classList.add('dialog-noshow');
})
// 댓글 등록 팝업창 닫기
document.querySelector('#reply-add-close-btn').addEventListener('click', (e) => {
    e.preventDefault();
    document.querySelector('#reply-add-popup').classList.add('dialog-noshow');
})
// 댓글 수정 팝업창 닫기
document.querySelector('#reply-update-close-btn').addEventListener('click', (e) => {
    e.preventDefault();
    document.querySelector('#reply-update-popup').classList.add('dialog-noshow');
})
