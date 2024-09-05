const getReviews = (travelNo, curPage) => {
    ajax({
        url: `/travel/review/${travelNo}?currentPage=${curPage}`,
        method: 'GET'
    }, (response) => {
        console.log(response);
        // review 구성하기
        const reviewRows = document.querySelector('#review-rows');
        reviewRows.replaceChildren();
        response.data.forEach((review) => {
            const oneReview = document.createElement('div');
            oneReview.className = 'one-review';

            // 개인 아이디 div
            const personalId = document.createElement('div');
            personalId.className = 'personal-id';
            personalId.textContent = review.memberId; // 사용자 아이디 넣기

            // content div
            const content = document.createElement('div');
            content.className = 'review-content';

            // content-top div
            const contentTop = document.createElement('div');
            contentTop.className = 'content-top';

            // 별점 (fa-star)
            const starContainer = document.createElement('p');
            starContainer.className = 'main-detail-content-photo-star';

            for (let i = 1; i <= 5; i++) {
                const star = document.createElement('span');
                star.className = i <= Math.floor(review.score) ? 'fa fa-star checked' : (i === Math.ceil(review.score) ? 'fa fa-star-half-o checked' : 'fa fa-star');
                starContainer.appendChild(star);
            }
            const scoreSpan = document.createElement('span');
            scoreSpan.textContent = review.score != null ? review.score : '0.0';
            scoreSpan.style.marginLeft = '8px';
            starContainer.appendChild(scoreSpan);

            // 버튼 메뉴
            const buttonMenu = document.createElement('div');
            buttonMenu.className = 'button-menu';

            const modifyBtn = document.createElement('button');
            modifyBtn.className = 'select-btn';
            modifyBtn.id = 'modify-btn';
            modifyBtn.textContent = '수정';

            const removeBtn = document.createElement('button');
            removeBtn.className = 'select-btn';
            removeBtn.id = 'remove-btn';
            removeBtn.textContent = '삭제';

            buttonMenu.appendChild(modifyBtn);
            buttonMenu.appendChild(removeBtn);

            // content-bottom (내용 추가)
            const contentBottom = document.createElement('div');
            contentBottom.className = 'content-bottom';
            contentBottom.textContent = review.reviewContent; // 리뷰 내용 넣기

            // content-top, button, content-bottom 추가
            contentTop.appendChild(starContainer);
            contentTop.appendChild(buttonMenu);
            content.appendChild(contentTop);
            content.appendChild(contentBottom);

            // 사진 섹션
            const photo = document.createElement('div');
            photo.className = 'photo';

            const img = document.createElement('img');
            img.className = 'thumbnail';
            img.alt = 'thumbnail';
            img.src = review.imageUrl != null ? review.imageUrl : '/img/no-image.png'; // 이미지 추가

            photo.appendChild(img);

            // 댓글 섹션 (비워둠)
            const reply = document.createElement('div');
            reply.className = 'reply';

            // 전체 구조를 one-review에 추가
            oneReview.appendChild(personalId);
            oneReview.appendChild(content);
            oneReview.appendChild(photo);
            oneReview.appendChild(reply);

            // reviewRows에 oneReview 추가
            reviewRows.appendChild(oneReview);

        })

        // pagination 구성하기
        const paginationUl = document.getElementById('pagination');
        paginationUl.replaceChildren();
        if(response.hasPrev) {
            const li = document.createElement("li");
            li.className = 'common-pagination-li-end';
            const prev = document.createElement('a');
            prev.innerHTML = '이전';
            prev.onclick = () => getReviews(travelNo, curPage - 1);
            // prev.href = `/travel/review/${travelNo}?currentPage=${curPage - 1}`;
            prev.innerHTML = '이전'
            li.appendChild(prev);
            paginationUl.appendChild(li);
        }
        for (let i = response.startNavi; i <= response.endNavi; i++) {
            const li = document.createElement('li');
            li.className = (i === curPage) ? 'common-pagination-li common-pagination-li-active' : 'common-pagination-li';
            const a = document.createElement('a');
            a.href = `javascript:void(0)`;
            a.textContent = i;
            a.onclick = () => getReviews(travelNo, i);
            // a.href = `/travel/review/${travelNo}?currentPage=${i}`;
            li.appendChild(a);
            paginationUl.appendChild(li);
        }
        if(response.hasNext) {
            const li = document.createElement("li");
            li.className = 'common-pagination-li-end';
            const next = document.createElement('a');
            // next.href = `/travel/review/${travelNo}?currentPage=${curPage + 1}`;
            next.href = `javascript:void(0)`; // 수정된 부분
            next.innerHTML = '다음';
            next.onclick = () => getReviews(travelNo, curPage + 1); // 수정된 부분
            li.appendChild(next);
            paginationUl.appendChild(li);
        }
    }, (error) => {
        console.log(error);
    })
}

const travelNo = document.querySelector('input[name="travelNo"]').value;
getReviews(travelNo, 1);