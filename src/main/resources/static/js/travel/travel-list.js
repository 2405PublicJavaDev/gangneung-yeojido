const getPage = (currentPage, travelName, region, category) => {
    let additionalQueryString = '';
    if (travelName) {
        additionalQueryString += '&travelName=' + travelName;
    }
    if (region) {
        additionalQueryString += '&region=' + region;
    }
    if (category) {
        additionalQueryString += '&category=' + category;
    }

    const root = document.querySelector('#main-bottom');
    root.replaceChildren();
    root.innerHTML = `<div class="circular-progress"></div>`;

    console.log('/travel?currentPage=' + currentPage + additionalQueryString);
    ajax({
        url: '/travel?currentPage=' + currentPage + additionalQueryString,
        method: 'get'
    }, (response) => {
        console.log(response);
        if (response.totalCount > 0) {
            const listUl = document.createElement('ul');
            listUl.className = 'travels';
            let listUlInnerHTML = '';

            // 이미지 로드 상태를 추적하기 위한 변수
            let imagesLoaded = 0;
            const totalImages = response.data.length;

            response.data.forEach((travel, index) => {
                // 이미지 태그를 만들고, onload 이벤트 핸들러를 설정
                const img = new Image();
                img.src = travel.imageUrl != null ? travel.imageUrl : '/img/sample_img.png';
                img.alt = "thumbnail";
                img.className = "thumbnail";
                const onDone = () => {
                    root.replaceChildren();
                    root.appendChild(listUl);

                    const paginationUl = document.createElement('ul');
                    paginationUl.className = 'common-pagination';
                    let paginationUlInnerHTML = '';
                    if (response.hasPrev) {
                        paginationUlInnerHTML += `
                                <li class="common-pagination-li-end">
                                    <a href="javascript:void(0);" onclick="getPage(${response.prevPage}, ${travelName}, ${region}, ${category})">이전</a>
                                </li>
                            `;
                    }
                    for (let i = response.startNavi; i <= response.endNavi; i++) {
                        paginationUlInnerHTML += `
                                <li class="${i == response.currentPage ? 'common-pagination-li common-pagination-li-active' : 'common-pagination-li'}">
                                    <a href="javascript:void(0);" onclick="getPage(${i}, ${travelName}, ${region}, ${category})">${i}</a>
                                </li>
                            `;
                    }
                    if (response.hasNext) {
                        paginationUlInnerHTML += `
                                <li class="common-pagination-li-end">
                                    <a href="javascript:void(0);" onclick="getPage(${response.nextPage}, ${travelName}, ${region}, ${category})">다음</a>
                                </li>
                            `;
                    }
                    paginationUl.innerHTML = paginationUlInnerHTML;
                    root.appendChild(paginationUl);

                    setTimeout(() => {
                        document.querySelector(".travels").style.opacity = '1';
                        document.querySelector(".travels").style.transform = 'translateY(0)';
                    }, 50);

                    const icons = document.querySelectorAll('.fa-bookmark');

                    icons.forEach(function (icon) {
                        icon.addEventListener('click', function () {
                            console.log('icon click');
                            this.classList.toggle('active');
                        });
                    });
                }

                img.onload = () => {
                    imagesLoaded++;
                    // 모든 이미지가 로드되었을 때 appendChild 실행
                    if (imagesLoaded === totalImages) {
                        onDone();
                    }
                };
                img.onerror = () => {
                    imagesLoaded++;
                    // 모든 이미지가 로드되었을 때 appendChild 실행
                    if (imagesLoaded === totalImages) {
                        onDone();
                    }
                }

                // 리스트에 항목 추가
                listUlInnerHTML += `
                    <li>
                        <div class="travel-image">
                            <a href="/travel/detail/${travel.travelNo}?currentPage=${currentPage}${additionalQueryString}">
                                ${img.outerHTML}
                            </a>
                        </div>
                        <div class="travel-detail">
                            <div class="travel-title">
                                <a href="/travel/detail/${travel.travelNo}?currentPage=${currentPage}${additionalQueryString}">${travel.travelName}</a>
                                <i class="fa-regular fa-bookmark"></i>
                            </div>
                            <div class="travel-address">
                                <div class="address-left">
                                    <i class="fa-solid fa-location-dot"></i>
                                </div>
                                <div class="address-right">
                                    <span class="address-text">${travel.address}</span>
                                </div>
                            </div>
                        </div>
                    </li>
                `;
            });

            listUl.innerHTML = listUlInnerHTML;
        } else {
            root.innerHTML = `<span class="travel-list-no-content">조회된 결과가 없습니다.</span>`;
        }

        window.scrollTo({
            top: 0,
        });
    }, (error) => {
        console.log(error);
    });
};

getPage(currentPage, travelName, region, category);

document.querySelector('#search-btn').addEventListener('click', (e) => {
    e.preventDefault();
    getPage(1, document.querySelector('#search-text').value, null, null);
});
