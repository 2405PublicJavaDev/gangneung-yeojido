const getPage = (currentPage, travelName, region, category) => {
    let additionalQueryString = '';
    if (travelName && travelName !== 'null' && travelName !== 'undefined') {
        additionalQueryString += '&travelName=' + travelName;
    }
    if (region  && region !== 'null' && region !== 'undefined') {
        additionalQueryString += region;
    }
    if (category && category !== 'null' && category !== 'undefined') {
        additionalQueryString += category;
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
                                    <a href="javascript:void(0);" onclick="getPage(${response.prevPage}, '${travelName}', '${region}', '${category}')">이전</a>
                                </li>
                            `;
                    }
                    for (let i = response.startNavi; i <= response.endNavi; i++) {
                        paginationUlInnerHTML += `
                                <li class="${i == currentPage ? 'common-pagination-li common-pagination-li-active' : 'common-pagination-li'}">
                                    <a href="javascript:void(0);" onclick="getPage(${i}, '${travelName}', '${region}', '${category}')">${i}</a>
                                </li>
                            `;
                    }
                    if (response.hasNext) {
                        paginationUlInnerHTML += `
                                <li class="common-pagination-li-end">
                                    <a href="javascript:void(0);" onclick="getPage(${response.nextPage}, '${travelName}', '${region}', '${category}')">다음</a>
                                </li>
                            `;
                    }
                    paginationUl.innerHTML = paginationUlInnerHTML;
                    root.appendChild(paginationUl);

                    setTimeout(() => {
                        if(document.querySelector(".travels")) {
                            document.querySelector(".travels").style.opacity = '1';
                            document.querySelector(".travels").style.transform = 'translateY(0)';
                        }
                    }, 50);

                    const icons = document.querySelectorAll('.fa-bookmark');

                    icons.forEach(function (icon) {
                        icon.addEventListener('click', function () {
                            if(memberId) {
                                const travelNo = icon.id.substring('favorite-'.length);
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
                                alert('로그인을 먼저 해주세요.')
                            }
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
                                <i class="fa-regular fa-bookmark ${travel.favoritesNo ? 'active' : ''}" data-value="${travel.favoritesNo}" id="favorite-${travel.travelNo}"></i>
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
    clearFilter();
    getPage(1, document.querySelector('#search-text').value, null, null);
});

document.querySelector('#condition-btn').addEventListener('click', (e) => {
    e.preventDefault();
    document.querySelector('#category-popup').classList.remove('dialog-noshow');
})

document.querySelector('#apply-btn').addEventListener('click', (e) => {
    e.preventDefault();
    document.querySelector('#category-popup').classList.add('dialog-noshow');
});

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
        applyFilter();
    });
});

const applyFilter = () => {
    let regionString = null;
    if(selectedRegion.length > 0) {
        regionString = "";
        selectedRegion.forEach((region) => {
            console.log(region);
            if(region === '주문진권') {
                regionString += "&region=JUMUNJIN";
            } else if (region === '대관령권') {
                regionString += "&region=DAEGWALLYEONG";
            } else if (region === '시내권') {
                regionString += "&region=CITY";
            } else if (region === '정동진옥계권'){
                regionString += "&region=JEONGDONGJIN";
            } else if (region === '경포권') {
                regionString += "&region=GYEONGPO";
            }
        })
    }
    let categoryString = null;
    if(selectedCategory.length > 0) {
        categoryString = "";
        selectedCategory.forEach((category) => {
            categoryString += "&category=" + category;
        })
    }
    getPage(1, null, regionString,categoryString);
}

const clearFilter = () => {
    selectedRegion = [];
    selectedCategory = [];
    buttons.forEach(button => {
        button.style.backgroundColor = '#FFFFFF';
        button.style.color = '#000000';
    });
    drawGangneung();
}