document.addEventListener('DOMContentLoaded', function() {
    // 페이지 로드 시 "정보" 버튼 클릭 상태로 설정
    selectInfo();

    // 페이지 로드 시 타이틀 천천히 로드
    document.querySelector("h1").style.opacity = '1';
    document.querySelector("#category-box").style.opacity = '1';
    document.querySelector("#score-box").style.opacity = '1';
    document.querySelector("h1").style.transform = 'translateY(0)';
    document.querySelector("#category-box").style.transform = 'translateY(0)';
    document.querySelector("#score-box").style.transform = 'translateY(0)';

    // 즐겨찾기 클릭 버튼 동작
    const icons = document.querySelectorAll('.fa-bookmark');
    icons.forEach(function(icon) {
        icon.addEventListener('click', function() {
            // 클릭한 아이콘에 active 클래스를 토글
            if(memberId) {
                if (!icon.dataset.value || icon.dataset.value === 'null') {
                    // 즐겨찾기를 추가한다.
                    ajax({
                        url: '/favorites',
                        method: 'post',
                        payload: {
                            'travelNo': travelNo
                        }
                    }, (response) => {
                        alert('즐겨찾기를 추가하였습니다.');
                        icon.dataset.value = response.favoritesNo;
                        this.classList.toggle('active');
                    }, (error) => {
                        alert('즐겨찾기 추가 과정 중에 오류가 발생하였습니다.');
                    });
                } else {
                    // 즐겨찾기를 제거한다
                    ajax({
                        url: '/favorite/' + icon.dataset.value,
                        method: 'delete'
                    }, (response) => {
                        alert('즐겨찾기를 해제하였습니다.');
                        icon.dataset.value = null;
                        this.classList.toggle('active');
                    }, (error) => {
                        alert('즐겨찾기 해제 과정 중에 오류가 발생하였습니다.');
                    });
                }
            } else {
                alert('로그인을 먼저 해주세요.');
                location.href = '/login';
            }
        });
    });
});

// 별점 스코어 가져오기
const score = parseFloat(document.querySelector("#stars").innerHTML);

// 별점을 렌더링할 컨테이너 선택
const starContainer = document.querySelector("#star-container");

// 기존 별점 텍스트 요소를 제거 (숫자 텍스트만 나타내기)
document.querySelector("#stars").style.display = 'none';

// 별점 렌더링
for (let i = 1; i <= 5; i++) {
    const star = document.createElement('span');
    let starClass = 'fa fa-star';  // 기본 빈 별

    // 별을 다 채우거나 반 채운 상태를 설정
    if (i <= Math.floor(score)) {
        starClass = 'fa fa-star checked';  // 가득 찬 별
    } else if (i === Math.ceil(score) && (score % 1 >= 0.5)) {
        starClass = 'fa fa-star-half-o checked';  // 반쯤 찬 별
    }

    star.className = starClass;

    // 빈 별의 색깔을 테두리만 칠하기 위해 추가 스타일 지정
    if (i > Math.ceil(score)) {
        star.style.color = 'transparent';  // 빈 별 내부를 투명하게
        star.style.webkitTextStroke = '2px #FFA500';  // 테두리는 노란색으로
    }

    // starContainer에 별 추가
    starContainer.appendChild(star);
}

// 숫자는 별점 뒤에 그대로 표시
const scoreText = document.createElement('span');
scoreText.innerText = score.toFixed(1); // 소수점 1자리까지 표시
starContainer.appendChild(scoreText);
scoreText.style.marginLeft = '8px';

const infoBtn = document.getElementById('info-btn');
const reviewBtn = document.getElementById('review-btn');
const infoSection = document.getElementById('info-section');
const reviewSection = document.getElementById('review-section');

// 버튼 클릭 시 해당 폼만 보이게 하며, 선택된 버튼과 미선택된 버튼 색상으로 구분
infoBtn.addEventListener('click', selectInfo); // 정보 선택 버튼
reviewBtn.addEventListener('click', selectReview); // 리뷰 선택 버튼

function selectInfo() {
    infoSection.style.display = 'block';
    reviewSection.style.display = 'none';
    infoBtn.classList.add('selected');
    infoBtn.classList.remove('unselected');
    reviewBtn.classList.add('unselected');
    reviewBtn.classList.remove('selected');
}
function selectReview() {
    reviewSection.style.display = 'block';
    infoSection.style.display = 'none';
    reviewBtn.classList.add('selected');
    reviewBtn.classList.remove('unselected');
    infoBtn.classList.add('unselected');
    infoBtn.classList.remove('selected');
}

document.querySelector("#go-diary").addEventListener('click', (e) => {
    e.preventDefault();
    if(memberId) {
        location.href = `/register-diary?travelNo=${travelNo}`;
    } else {
        alert('로그인을 먼저 해주세요.');
        location.href = '/login';
    }
})
