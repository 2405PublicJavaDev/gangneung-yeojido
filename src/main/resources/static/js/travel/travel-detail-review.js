// 리뷰 수정
const modify = (reviewNo) => {
    ajax({
        url: `/review/modify/${travelNo}`,
        method: 'GET'
    }, (response) => {
        console.log(response);
        document.querySelector('#review-update-no').value = response.reviewNo;
        document.querySelector('#review-update-popup').classList.remove('dialog-noshow');

        document.querySelector('#review-content').value = response.reviewContent;
        document.querySelector('#rating-update-value').textContent = response.score.toFixed(1);

        const stars = document.querySelectorAll('#review-update-popup .fa-star');
        const score = response.score;

        stars.forEach((star, index) => {
            star.classList.remove('star-checked');
            const starValue = index + 1;

            if (starValue <= Math.floor(score)) {
                star.classList.add('star-checked');
            } else if (starValue === Math.ceil(score) && score % 1 !== 0) {
                star.classList.add('fa-star-half-o');
            } else {
                star.classList.remove('fa-star-half-o');
            }
        });

        const updateThumbnailsAfterLoading = async () => {
            for (const reviewFile of response.reviewFiles) {
                await inputFileDataHandler['review-modify-file-upload'].loadImageFromURL(reviewFile.webPath);
            }
        }
        updateThumbnailsAfterLoading().then(() => {
            inputFileDataHandler['review-modify-file-upload'].thumbnailUpdate();
        });
    }, (error) => {
        console.log(error);
    });
}
// 리뷰&댓글 삭제
const remove = (reviewNo) => {
    if (confirm("삭제하시겠습니까?")) {
        ajax({
            url: `/review/remove/${reviewNo}`,
            method: 'delete'
        }, (response) => {
            console.log(response);
            alert("삭제가 완료되었습니다.");
            location.href = `/travel/detail/${travelNo}`;
        }, (error) => {
            console.log(error);
        });
    }
}
// 리뷰 신고
const complain = (reviewNo) => {
    if (confirm("신고 페이지로 이동합니다.")) {
        location.href = `/review/complain/${travelNo}?reviewNo=${reviewNo}`;
    }
}
// 댓글 보기
const seeReply = (reviewNo) => {
    const replySection = document.querySelector(`#review-reply-${reviewNo}`);
    if(replySection.style.display === 'block') {
        replySection.style.display = 'none';
    }else {
        replySection.style.display = 'block';
        getReviewsReply(travelNo, reviewNo, 1);
    }
}
// 댓글 추가
const addReply = (reviewNo) => {
    parentReviewNo = reviewNo;
    document.querySelector('#reply-add-popup').classList.remove('dialog-noshow');
}
// 댓글 수정
const modifyReply = (reviewNo) => {
    ajax({
        url: `/reply/modify/${reviewNo}`,
        method: 'GET'
    }, (response) => {
        console.log(response);
        document.querySelector('#reply-update-popup').classList.remove('dialog-noshow');
        document.querySelector('#reply-update-no').value = response.reviewNo;
        document.querySelector('#reply-update-content').value = response.reviewContent;
    }, (error) => {
        console.log(error);
    });
}
// 페이지네이션 동작
const handlePagination = (page, reviewNo) => {
    if (reviewNo) {
        // 답글인 경우(PARENT_REVIEW_NO 가 NULL 이 아닐때)
        getReviewsReply(travelNo, reviewNo, page);
        scrollToPosition(700);  // 원하는 높이로 조정
    } else {
        // 리뷰인 경우(PARENT_REVIEW_NO 가 NULL 일때)
        getReviews(travelNo, page);
        scrollToPosition(600);  // 원하는 높이로 조정
    }
}
// 페이지 버튼 클릭 시 상단으로 이동
const scrollToPosition = (position) => {
    window.scrollTo({ top: position, behavior: 'smooth' });
};
// 한개의 답글 돔 구성
const replyToDom = (review) => {
    const isMyReview = memberId === review.memberId;
    return `
        <div class="one-reply">
            <div class="personal-id" style="${isMyReview ? 'font-weight: bold;' : ''}">
                ${isMyReview ? '나의 댓글' : review.memberId}</div>
            <div class="reply-content">
                <div class="content-top">
                    <span class="regDate">${new Date(review.regDate).toLocaleString('ko-KR', { month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: true })
                        .replace(/(\d{2})\/(\d{2})\/(\d{2})/, '$2.$3')
                        .replace('AM', '오전')
                        .replace('PM', '오후')
                        .replace(',', '')}</span>
                    <div class="button-menu">
                        ${isMyReview ? `
                            <button type="button" class="reply-select-btn reply-modify-btn" onclick="modifyReply(${review.reviewNo});">수정</button>
                            <button type="button" class="reply-select-btn reply-delete-btn" onclick="remove(${review.reviewNo});">삭제</button>
                        ` : `
                            <button type="button" class="reply-select-btn reply-complain-btn" onclick="complain(${review.reviewNo});">신고</button>
                        `}
                    </div>
                </div>
                <div class="content-bottom">${review.reviewContent}</div>
                <div class="reply-btn-section" id="reply-btn-${review.reviewNo}">
                    ${!review.parentReviewNo ? `
                        <button type="button" onclick="seeReply(${review.reviewNo})" style="margin-right: 10px;">댓글창</button>
                        <button onclick="addReply(${review.reviewNo})">댓글 달기</button>
                    ` : ''}
                </div>
                <div class="reply-section" id="review-reply-${review.reviewNo}"></div>
            </div>
        </div>
    `;
}
// 한개의 리뷰 돔 구성
const reviewToDom = (review) => {
    const isMyReview = memberId === review.memberId;
    return `
        <div class="one-review">
            <div class="personal-id" style="${isMyReview ? 'font-weight: bold;' : ''}">
                ${isMyReview ? '나의 리뷰' : review.memberId}</div>
            <div class="review-content">
                <div class="content-top">
                    <p class="main-detail-content-photo-star">
                        ${Array.from({ length: 5 }, (_, i) => {
                            let starClass = 'fa fa-star'; // 빈 별
                            if (i < Math.floor(review.score)) {
                                starClass = 'fa fa-star checked'; // 별 한개
                            } else if (i === Math.floor(review.score) && (review.score % 1 >= 0.5)) {
                                starClass = 'fa fa-star-half-o checked'; // 별 반개
                            }
                            return `<span class="${starClass}"></span>`;
                        }).join('')}
                        <span style="margin-left: 8px;">${review.score != null ? review.score.toFixed(1) : '0.0'}</span>
                    </p>
                    <span class="regDate">${new Date(review.regDate).toISOString().split('T')[0]}</span>
                    <div class="button-menu">
                        ${isMyReview ? `
                            <button type="button" class="select-btn review-modify-btn" onclick="modify(${review.reviewNo});">수정</button>
                            <button type="button" class="select-btn review-delete-btn" onclick="remove(${review.reviewNo});">삭제</button>
                        ` : `
                            <button type="button" class="select-btn review-complain-btn" onclick="complain(${review.reviewNo});">신고</button>
                        `}
                    </div>
                </div>
                <div class="content-bottom">${review.reviewContent}</div>
                <div class="content-img">
                    ${review.reviewFiles.map(file => `
                        <img src="${file.webPath}" />
                    `).join('')}
                </div>
                <div class="reply-btn-section" id="reply-btn-${review.reviewNo}">
                    ${!review.parentReviewNo ? `
                        <button type="button" onclick="seeReply(${review.reviewNo})" style="margin-right: 10px;">댓글창</button>
                        <button onclick="addReply(${review.reviewNo})">댓글 달기</button>
                    ` : ''}
                </div>
                <div class="reply-section" id="review-reply-${review.reviewNo}"></div>
            </div>
        </div>
    `;
}

// 페이지네이션 돔 구성
const buildPagination = (response, curPage, reviewNo) => {
    let paginationHtml = '<ul class="common-pagination">';

    if (response.hasPrev) {
        paginationHtml += `
            <li class="common-pagination-li-end">
                <a href="javascript:void(0);" onclick="handlePagination(${curPage - 1}, ${reviewNo})">이전</a>
            </li>
        `;
    }

    for (let i = response.startNavi; i <= response.endNavi; i++) {
        paginationHtml += `
            <li class="${i === curPage ? 'common-pagination-li common-pagination-li-active' : 'common-pagination-li'}">
                <a href="javascript:void(0);" onclick="handlePagination(${i}, ${reviewNo})">${i}</a>
            </li>
        `;
    }

    if (response.hasNext) {
        paginationHtml += `
            <li class="common-pagination-li-end">
                <a href="javascript:void(0);" onclick="handlePagination(${curPage + 1}, ${reviewNo})">다음</a>
            </li>
        `;
    }

    paginationHtml += '</ul>';
    return paginationHtml;
}

// 댓글 목록 가져오기
const getReviewsReply = (travelNo, reviewNo, curPage) => {
    ajax({
        url: `/travel/review?currentPage=${curPage}&travelNo=${travelNo}&reviewNo=${reviewNo}`,
        method: 'GET'
    }, (response) => {
        const reply = document.querySelector('#review-reply-' + reviewNo);
        reply.replaceChildren();
        if (response.data.length === 0) {
            const noReviewsMessage = document.createElement('h2');
            noReviewsMessage.textContent = '등록된 내용이 없습니다';
            noReviewsMessage.className = 'no-reviews-message';
            reply.appendChild(noReviewsMessage);
        } else {
            response.data.forEach((review) => {
                reply.innerHTML += replyToDom(review);
            });
            reply.innerHTML += buildPagination(response, curPage, reviewNo);
        }
    }, (error) => {
        console.log(error);
    });
}

// 리뷰창 보여주기
const getReviews = (travelNo, curPage) => {
    // 내 리뷰 보여주기
    ajax({
        url: `/travel/myreview/${travelNo}`,
        method: 'GET'
    }, (response) => {
        console.log(response);
        const myReviewTag = document.querySelector('#my-review');
        myReviewTag.innerHTML = '';
        const registerBtn = document.createElement('button');
        // 내가 쓴 리뷰가 있으면
        if(response.reviewNo) {
            myReviewTag.replaceChildren();
            console.log(response);
            myReviewTag.innerHTML += reviewToDom(response);
        // 내가 쓴 리뷰가 없으면
        } else {
            const registerBtn = document.createElement('button');
            registerBtn.id = 'register-review-btn';
            registerBtn.textContent = '리뷰 작성';
            myReviewTag.appendChild(registerBtn);
            registerBtn.addEventListener('click', () => {
                document.querySelector('#review-add-popup').classList.remove('dialog-noshow');
            });
        }
    }, (error) => {
        console.log(error);
    });
    // 댓글창 보여주기
    ajax({
        url: `/travel/review?currentPage=${curPage}&travelNo=${travelNo}`,
        method: 'GET'
    }, (response) => {
        console.log(response);
        // review 구성하기
        const reviewRows = document.querySelector('#review-rows');
        reviewRows.innerHTML = ''; // 기존 내용을 제거
        reviewRows.replaceChildren();
        if (response.data.length === 0) {
            const noReviewsMessage = document.createElement('h2');
            noReviewsMessage.textContent = '등록된 내용이 없습니다';
            noReviewsMessage.className = 'no-reviews-message';
            reviewRows.appendChild(noReviewsMessage);
        } else {
            response.data.forEach((review) => {
                reviewRows.innerHTML += reviewToDom(review);
            });
            reviewRows.innerHTML += buildPagination(response, curPage, null);
        }
    }, (error) => {
        console.log(error);
    })
}
const travelNo = document.querySelector('input[name="travelNo"]').value;
getReviews(travelNo, 1);
// parentReviewNo를 전역변수로 선언
let parentReviewNo = null;
// 초기엔 리뷰작성 팝업을 보여주지 않음
let dialogShown = false;