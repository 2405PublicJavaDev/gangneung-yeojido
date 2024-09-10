// 모든 버튼을 선택합니다.
const buttons = document.querySelectorAll('button[id^="category"]');
const buttonBackground = new Map();
buttonBackground.set("MOUNTAIN", "#00A155");
buttonBackground.set("SEA", "#007FFF");
buttonBackground.set("PARK", "#FF7E04");
buttonBackground.set("HISTORY", "#A064FF");
buttonBackground.set("DISPLAY", "#FF4274");
buttonBackground.set("LEISURE", "#18BDAA");
// 각 버튼에 대해 이벤트를 추가합니다.
buttons.forEach(button => {
    // id에서 'category' 이후의 부분을 추출합니다.
    const idSuffix = button.id.replace('category', '');

    // 이벤트 리스너를 추가합니다. (여기서는 예시로 클릭 이벤트를 사용합니다.)
    button.addEventListener('click', () => {
        // 추가적인 동작을 여기에 작성할 수 있습니다.
        const index = selectedCategory.indexOf(idSuffix);
        if (index !== -1) {
            selectedCategory.splice(index, 1);
            button.style.backgroundColor = '#FFFFFF';
            button.style.color = '#000000';
        } else {
            selectedCategory.push(idSuffix);
            button.style.backgroundColor = buttonBackground.get(idSuffix);
            button.style.color = '#FFFFFF';
        }
        drawGangneung();
    });
});

const getOutline = (travelNo) => {
    const notLoadTag = document.querySelector("#main-detail-content-not-load");
    const contentTag = document.querySelector("#main-detail-content");
    notLoadTag.style.display = 'flex';
    contentTag.style.display = 'none';
    Promise.all([new Promise((resolve, reject) => {
        ajax({url: `/travel/${travelNo}`, method: 'get'},
            (response) => {
                console.log(response, response.imageUrl);
                const img = new Image;
                img.src = response.imageUrl;
                img.onload = () => {
                    response.img = img;
                    resolve(response);
                }
                img.onerror = (e) => {
                    resolve(response);
                }
            }, (error) => {reject(error);})
    }), new Promise((resolve, reject)=> {
        setTimeout(() => {
            resolve("Resolved after 1 second");
        }, 1000); // 1000ms = 1초
    })])
        .then((data) => data[0])
        .then((response) => {
        contentTag.style.display = 'flex';
        const imgTag = document.querySelector("#main-detail-content-photo-img");
        if(response.img) {
            imgTag.src = response.img.src;
        } else {
            imgTag.src = '/img/no-image.png';
        }
        const titleTag = document.querySelector("#main-detail-content-photo-name");
        titleTag.innerHTML = response.travelName;
        const textTag = document.querySelector("#main-detail-content-text");
        const maxLength = 120; // 최대 길이
        if (response.introduce.length > maxLength) {
            textTag.innerHTML = response.introduce.substring(0, maxLength) + "...";
        } else {
            textTag.innerHTML = response.introduce;
        }
        const starContainer = document.querySelector('#main-detail-content-photo-star');
        starContainer.replaceChildren();
        const score = response.score;
        // 별점 렌더링
        for (let i = 1; i <= 5; i++) {
            const star = document.createElement('span');

            // 별을 다 채우거나 반 채운 상태를 설정
            if (i <= Math.floor(score)) {
                star.className = 'fa fa-star checked';  // 가득 찬 별
            } else if (i === Math.ceil(score) && score % 1 !== 0) {
                star.className = 'fa fa-star-half-o checked';  // 반쯤 찬 별
            } else {
                star.className = 'fa fa-star unchecked';  // 빈 별
            }

            // starContainer에 별 추가
            starContainer.appendChild(star);
        }
        const starDisplay = document.createElement('span');
        starDisplay.innerHTML = score;
        starContainer.appendChild(starDisplay);
        const moreBtn = document.createElement('button');
        moreBtn.id = 'main-detail-more';
        moreBtn.innerHTML = '자세히 보기'
        moreBtn.addEventListener('click', (e) => {
            e.preventDefault();
            location.href = '/travel/detail/'+ response.travelNo;
        })
        const mainDetailContent = document.querySelector('#main-detail-content');
        const oldMoreBtn = document.querySelector('#main-detail-more');
        if(oldMoreBtn) {
            oldMoreBtn.outerHTML = "";
        }
        mainDetailContent.appendChild(moreBtn);
        notLoadTag.style.display = 'none';
    }).catch((error) => {
        console.log('error', error);
        notLoadTag.style.display = 'block';
        notLoadTag.innerHTML = '오류가 발생햇습니다.'
    })

}

getOutline(randomNumber);

const icons = document.querySelectorAll('.fa-bookmark');

icons.forEach(function(icon) {
    icon.addEventListener('click', function() {
        // 클릭한 아이콘에 active 클래스를 토글
        this.classList.toggle('active');
    });
});

// Initialize Swiper
const swiper = new Swiper('.swiper', {
    // Optional parameters
    direction: 'horizontal',
    loop: true,

    // Pagination
    pagination: {
        el: '.swiper-pagination',
    },

    // Navigation
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },

    // Autoplay
    autoplay: {
        delay: 2500,
        disableOnInteraction: false,
    },
    slidesPerView: 'auto',
    centeredSlides: true,
});

const requestAddMark = () => {
    if(memberId) {
        if (memberStatus === 'BLACK') {
            alert('블랙된 상태입니다. 따라서 마커 추가 요청을 할 수 없습니다.');
        } else {
            location.href = "/mark-request";
        }
    } else {
        alert('로그인을 먼저 해주세요');
        location.href = "/login";
    }
}