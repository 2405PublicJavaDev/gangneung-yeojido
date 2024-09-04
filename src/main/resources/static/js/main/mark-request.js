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
    ajax({
        url: '/mark-request',
        method: 'post',
        payload: formProps
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