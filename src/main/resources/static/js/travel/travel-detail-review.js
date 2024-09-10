// 한개의 리뷰 생성
const reviewToDom = (review) => {
    const oneReview = document.createElement('div');
    oneReview.className = 'one-review';
    if(memberId === review.memberId) {
        oneReview.style.backgroundColor = "#f8f8ff";
    }

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
    scoreSpan.textContent = review.score != null ? review.score.toFixed(1) : '0.0';
    scoreSpan.style.marginLeft = '8px';
    starContainer.appendChild(scoreSpan);

    const regDate = document.createElement('span');
    regDate.className = 'regDate';
    const date = new Date(review.regDate);  // timestamp를 Date 객체로 변환
    const formattedDate = date.toISOString().split('T')[0];  // 'yyyy-mm-dd' 형식으로 변환
    regDate.textContent = formattedDate;

    // 버튼 메뉴
    const buttonMenu = document.createElement('div');
    buttonMenu.className = 'button-menu';

    // 내가 쓴 리뷰는 수정, 삭제 버튼 / 아니면 신고 버튼
    if(review.memberId === memberId) {
        personalId.innerHTML = "나의 리뷰";
        personalId.style.fontWeight = 'bold';
        const modifyBtn = document.createElement('button');
        modifyBtn.className = 'select-btn';
        modifyBtn.id = 'modify-btn';
        modifyBtn.textContent = '수정';

        modifyBtn.addEventListener('click', (e) => {
            e.preventDefault();
            ajax({
                url: `/review/modify/${travelNo}`,
                method: 'GET'
            }, (response) => {
                console.log(response);
                document.querySelector('#review-update-no').value = response.reviewNo;
                // 팝업 창 보여주기
                document.querySelector('#review-update-popup').classList.remove('dialog-noshow');

                // 가져온 데이터로 팝업 필드 채우기
                document.querySelector('#review-content').value = response.reviewContent; // 리뷰 내용
                document.querySelector('#rating-update-value').textContent = response.score.toFixed(1); // 리뷰 점수

                // 별점 채우기
                const stars = document.querySelectorAll('#review-update-popup .fa-star');
                const score = response.score;

                stars.forEach((star, index) => {
                    star.classList.remove('star-checked');  // 기존 색칠된 별 초기화
                    const starValue = index + 1;

                    // 현재 별이 점수보다 작거나 같으면 채우기
                    if (starValue <= Math.floor(score)) {
                        star.classList.add('star-checked');
                    } else if (starValue === Math.ceil(score) && score % 1 !== 0) {
                        // 반 개 별 처리 (소수점 존재 시 반 개 별 색칠)
                        star.classList.add('fa-star-half-o');
                    } else {
                        star.classList.remove('fa-star-half-o');
                    }
                });

                // 이미지 채우기
                const updateThumbnailsAfterLoading = async () => {
                    for (const reviewFile of response.reviewFiles) {
                        await inputFileDataHandler['review-modify-file-upload'].loadImageFromURL(reviewFile.webPath);
                    }
                }
                updateThumbnailsAfterLoading().then(() => {
                    inputFileDataHandler['review-modify-file-upload'].thumbnailUpdate();
                });
                //
            }, (error) => {
                console.log(error);
            });
        })

        const removeBtn = document.createElement('button');
        removeBtn.className = 'select-btn';
        removeBtn.id = 'remove-btn';
        removeBtn.textContent = '삭제';

        removeBtn.addEventListener('click', (e) => {
            e.preventDefault();
            if(confirm("리뷰를 삭제하시겠습니까?")) {
                ajax({
                    url: `/review/remove/${review.reviewNo}`,
                    method: 'delete'
                }, (response) => {
                    console.log(response);
                    alert("리뷰 삭제가 완료되었습니다.");
                    location.href=`/travel/detail/${travelNo}`;
                }, (error) => {
                    console.log(error);
                });
            }
        })

        buttonMenu.appendChild(modifyBtn);
        buttonMenu.appendChild(removeBtn);
    } else {
        const complainBtn = document.createElement('button');
        complainBtn.className = 'select-btn';
        complainBtn.id = 'complain-btn';
        complainBtn.textContent = '신고'
        complainBtn.style.backgroundColor = '#EE450C';

        complainBtn.addEventListener('mouseenter', function() {
            complainBtn.style.backgroundColor = '#fc6e40';
        })
        complainBtn.addEventListener('mouseleave', function() {
            complainBtn.style.backgroundColor = '#EE450C';
        })
        complainBtn.addEventListener('click', (e) => {
            e.preventDefault();
            if(confirm("신고 페이지로 이동합니다.")) {
                location.href= `/review/complain/${travelNo}?reviewNo=${review.reviewNo}`;
            }
        })
        buttonMenu.appendChild(complainBtn);
    }

    // content-bottom (내용 추가)
    const contentBottom = document.createElement('div');
    contentBottom.className = 'content-bottom';
    contentBottom.textContent = review.reviewContent; // 리뷰 내용 넣기

    // content-img (이미지 추가)
    const contentImg = document.createElement('div');
    contentImg.className = 'content-img';
    review.reviewFiles.forEach((reviewFile) => {
        const reviewImg = document.createElement('img');
        reviewImg.src = reviewFile.webPath;
        contentImg.appendChild(reviewImg);
    })

    // 버튼창 div
    const replyBtn = document.createElement('div');
    replyBtn.className = 'reply-btn-section';
    replyBtn.id = 'reply-btn';

    // 댓글창 div
    const replyDiv = document.createElement('div');
    replyDiv.className = 'reply-section';
    replyDiv.id = `review-reply-${review.reviewNo}`;
    // replyDiv.style.display = 'none';  // 초기엔 숨김

    if(!review.parentReviewNo) {
        const seeReplyBtn = document.createElement('button');
        seeReplyBtn.innerHTML = '댓글창';
        seeReplyBtn.style.marginRight = '10px';
        let isReplyOpen = false;
        // 답글창 열기&닫기
        seeReplyBtn.addEventListener('click', (e) => {
            e.preventDefault();
            const replySection = document.querySelector(`#review-reply-${review.reviewNo}`);

            if(isReplyOpen) {
                replySection.style.display = 'none';
                isReplyOpen = false;
            }else {
                replySection.style.display = 'block';
                getReviewsReply(travelNo, review.reviewNo, 1);
                isReplyOpen = true;
            }
        })
        // 버튼들을 replyDiv 아래에 위치하게 하여 버튼이 사라지지 않도록 함
        // const replyButtonsContainer = document.createElement('div');
        // replyButtonsContainer.className = 'reply-buttons-container';
        replyBtn.appendChild(seeReplyBtn);
        const addReplyBtn = document.createElement('button');
        addReplyBtn.innerHTML = '댓글 달기';
        addReplyBtn.addEventListener('click', (e) => {
            e.preventDefault();
            parentReviewNo = review.reviewNo;
            document.querySelector('#reply-add-popup').classList.remove('dialog-noshow');
        });
        replyBtn.appendChild(addReplyBtn);
        // replyDiv.appendChild(replyButtonsContainer);
    }

    // content-top, button, content-bottom, content-img 추가
    contentTop.appendChild(starContainer);
    contentTop.appendChild(regDate);
    contentTop.appendChild(buttonMenu);
    content.appendChild(contentTop);
    content.appendChild(contentBottom);
    content.appendChild(contentImg);

    // reply-section을 content 아래에 추가
    content.appendChild(replyBtn);
    content.appendChild(replyDiv);

    // 전체 구조를 one-review에 추가
    oneReview.appendChild(personalId);
    oneReview.appendChild(content);

    return oneReview;
}

// 리뷰 돔 생성
const buildReviewsDom = (response, curPage, reviewRows, reviewNo) => {
    if (response.data.length === 0) {
        const noReviewsMessage = document.createElement('h2');
        noReviewsMessage.textContent = '등록된 내용이 없습니다';
        noReviewsMessage.className = 'no-reviews-message';
        reviewRows.appendChild(noReviewsMessage);
    } else {
        // 내가 쓴 리뷰 먼저 추가 (중복 방지)
        const myReview = response.data.find(review => review.memberId === memberId);
        if (myReview) {
            reviewRows.appendChild(reviewToDom(myReview));
        }
        // 전체 리뷰 추가
        response.data.forEach((review) => {
            // 중복되지 않도록 내 리뷰는 다시 추가하지 않음
            if (review.memberId !== memberId) {
                reviewRows.appendChild(reviewToDom(review));
            }
        });
    }

    // pagination 구성하기
    const paginationUl = document.createElement('ul');
    paginationUl.className = 'common-pagination';
    // 페이지 버튼 클릭 시 상단으로 이동
    const scrollToPosition = (position) => {
        window.scrollTo({ top: position, behavior: 'smooth' });
    };
    if(response.hasPrev) {
        const li = document.createElement("li");
        li.className = 'common-pagination-li-end';
        const prev = document.createElement('a');
        prev.innerHTML = '이전';
        prev.onclick = () => {
            if(reviewNo) {
                getReviewsReply(travelNo, reviewNo, curPage - 1);
                scrollToPosition(600);  // 원하는 높이로 조정
            } else {
                getReviews(travelNo, curPage - 1);
                scrollToPosition(600);  // 원하는 높이로 조정
            }
        };
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
        a.onclick = () => {
            if(reviewNo) {
                getReviewsReply(travelNo, reviewNo, i);
                scrollToPosition(600);  // 원하는 높이로 조정
            } else {
                getReviews(travelNo, i);
                scrollToPosition(600);  // 원하는 높이로 조정
            }
        };
        li.appendChild(a);
        paginationUl.appendChild(li);
    }
    if(response.hasNext) {
        const li = document.createElement("li");
        li.className = 'common-pagination-li-end';
        const next = document.createElement('a');
        next.href = `javascript:void(0)`;
        next.innerHTML = '다음';
        next.onclick = () => {
            if(reviewNo) {
                getReviewsReply(travelNo, reviewNo, curPage + 1);
                scrollToPosition(600);  // 원하는 높이로 조정
            } else {
                getReviews(travelNo, curPage + 1);
                scrollToPosition(600);  // 원하는 높이로 조정
            }
        };
        li.appendChild(next);
        paginationUl.appendChild(li);
    }
    reviewRows.appendChild(paginationUl);
}

// 댓글 목록 가져오기
const getReviewsReply = (travelNo, reviewNo, curPage) => {
    ajax({
        url: `/travel/review?currentPage=${curPage}&travelNo=${travelNo}&reviewNo=${reviewNo}`,
        method: 'GET'
    }, (response) => {
        const reply = document.querySelector('#review-reply-' + reviewNo);
        reply.replaceChildren();
        buildReviewsDom(response, curPage, reply, reviewNo);
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
            myReviewTag.appendChild(reviewToDom(response));
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
        buildReviewsDom(response, curPage, reviewRows);
    }, (error) => {
        console.log(error);
    })
}
// const parentReviewNo = document.querySelector('input[name="reviewNo"]').value;
const travelNo = document.querySelector('input[name="travelNo"]').value;
getReviews(travelNo, 1);
// parentReviewNo를 전역변수로 선언
let parentReviewNo = null;
// 초기엔 리뷰작성 팝업을 보여주지 않음
let dialogShown = false;