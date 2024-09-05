document.addEventListener('DOMContentLoaded', function() {
    const registerReviewBtn = document.getElementById('register-review');
    const popupContainer = document.getElementById('popup-container');

    registerReviewBtn.addEventListener('click', function() {
        loadPopup();
    });

    function loadPopup() {
        fetch('/review-popup.html')
            .then(response => response.text())
            .then(html => {
                popupContainer.innerHTML = html;
                initializePopup();
            })
            .catch(error => {
                console.error('팝업 로드 실패:', error);
                alert('리뷰 팝업을 로드하는 데 실패했습니다. 나중에 다시 시도해 주세요.');
            });
    }

    function initializePopup() {
        const reviewPopup = document.getElementById('review-popup');
        const submitReviewBtn = document.getElementById('submit-review');
        const cancelReviewBtn = document.getElementById('cancel-review');
        const starRating = document.querySelectorAll('.star-rating .fa-star');
        let selectedRating = 0;

        if (!reviewPopup || !submitReviewBtn || !cancelReviewBtn) {
            console.error('필요한 팝업 요소를 찾을 수 없습니다.');
            alert('리뷰 팝업을 초기화하는 데 문제가 발생했습니다. 페이지를 새로고침한 후 다시 시도해 주세요.');
            return;
        }

        starRating.forEach(star => {
            star.addEventListener('click', function() {
                selectedRating = this.getAttribute('data-rating');
                updateStars();
            });
        });

        function updateStars() {
            starRating.forEach(star => {
                if (star.getAttribute('data-rating') <= selectedRating) {
                    star.classList.add('checked');
                } else {
                    star.classList.remove('checked');
                }
            });
        }

        submitReviewBtn.addEventListener('click', function() {
            const reviewContent = document.getElementById('review-content');
            const reviewFile = document.getElementById('review-file');
            const travelNoInput = document.querySelector('input[name="travelNo"]');

            if (!reviewContent || !reviewFile || !travelNoInput) {
                console.error('필요한 입력 요소를 찾을 수 없습니다.');
                alert('리뷰 제출에 필요한 정보를 찾을 수 없습니다. 페이지를 새로고침한 후 다시 시도해 주세요.');
                return;
            }

            const formData = new FormData();
            formData.append('travelNo', travelNoInput.value);
            formData.append('reviewContent', reviewContent.value);
            formData.append('score', selectedRating);
            if (reviewFile.files[0]) {
                formData.append('file', reviewFile.files[0]);
            }

            ajax({
                url: '/review/add',
                method: 'POST',
                data: formData,
                processData: false,
                contentType: false
            }, (response) => {
                console.log('리뷰 등록 성공:', response);
                closePopup();
                getReviews(travelNoInput.value, 1);
            }, (error) => {
                console.error('리뷰 등록 실패:', error);
                alert('리뷰 등록에 실패했습니다. 나중에 다시 시도해 주세요.');
            });
        });

        cancelReviewBtn.addEventListener('click', closePopup);

        function closePopup() {
            popupContainer.innerHTML = '';
        }

        reviewPopup.style.display = 'block';
    }
});
