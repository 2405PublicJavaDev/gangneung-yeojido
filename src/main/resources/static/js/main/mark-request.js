document.querySelector("#search-address-btn").addEventListener("click", (e) => {
    const lat = document.querySelector("#latitude")
    const lon = document.querySelector("#longitude");
    const addressTag = document.querySelector("#address");
    new daum.Postcode({
        oncomplete: function(data) {
            Promise.resolve(data).then(o => {
                const mainAddress = data.roadAddress || data.jibunAddress;
                addressTag.value = mainAddress;
                return new Promise((resolve, reject) => {
                    const geocoder = new daum.maps.services.Geocoder();
                    geocoder.addressSearch(mainAddress, (result, status) =>{
                        if(status === daum.maps.services.Status.OK){
                            const { x, y } = result[0];

                            resolve({ lat: y, lon: x })
                        }else{
                            reject();
                        }
                    });
                })
            }).then(result => {
                // 위, 경도 결과 값
                console.log(result);
                lat.value = result.lat;
                lon.value = result.lon;
            }).catch((error) => {
                console.log(error);
                lat.value = '';
                lon.value = '';
            });
        },
    }).open();
})
document.querySelector("#request-mark-btn").addEventListener("click", (e) => {
    const formTag = document.querySelector("#request-mark-form");
    const formData = new FormData(formTag);
    const formProps = Object.fromEntries(formData);
    if(!travelNameCheck()) {
        elTravelName.scrollIntoView({behavior: 'smooth', block: 'start'});
        return;
    }
    ajax({
        url: '/mark-request',
        method: 'post',
        payload: formProps,
        blockValidateForm: true
    }, (response) => {
        console.log(response);
        alert('마커 승인을 요청하였습니다.')
        location.href="/";
    }, (error) => {
        alert('마커 추가 요청중 에러가 발생했습니다.');
        console.log(error);
    })
})
document.querySelector("#cancel-btn").addEventListener("click", (e) => {
    location.href="/";
})

const elTravelName = document.querySelector('#travelName');
const travelNameSuccessMessage = document.querySelector('#travel-name-success-message');
const travelNameFailureMessage = document.querySelector('#travel-name-failure-message');
elTravelName.onkeyup = function () {
    travelNameCheck();
}
const validTravelName = (str) => {
    return str.length > 0;
}
const travelNameCheck = () => {
    if (!validTravelName(elTravelName.value)) {
        travelNameFailureMessage.innerHTML = '여행지는 빈칸이면 안됩니다.';
        travelNameSuccessMessage.classList.add('hide');
        travelNameFailureMessage.classList.remove('hide');
        elTravelName.classList.add('border-red');
        return false;
    }
    travelNameFailureMessage.classList.add('hide');
    elTravelName.classList.remove('border-red');
    travelNameSuccessMessage.innerHTML = '사용할 수 있는 여행지명입니다.';
    travelNameSuccessMessage.classList.remove('hide');
    return true;
}
