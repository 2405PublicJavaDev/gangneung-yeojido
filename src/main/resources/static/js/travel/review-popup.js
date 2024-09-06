document.querySelector('#close').addEventListener('click', () => {
    closeDialog();
})

document.querySelector('#register').addEventListener('click', (e) => {
    e.preventDefault();
    const formTag = document.querySelector("#uploadForm");
    const formData = new FormData(formTag);
    // 파일을 FormData에 추가
    inputFileData.forEach(file => {
        formData.append('uploadFile', file);
    });
    formData.append('score',  document.querySelector('#rating-value').innerHTML);
    formData.append('travelNo', document.querySelector('input[name="travelNo"]').value);
    ajax({
            url: '/review/add',
            method: 'post',
            payload: formData,
            isMultipart: true
        },
        (response) => {
            console.log('response', response);
            location.href=`/travel/detail/${travelNo}`;
        },
        (error) => {
            console.log('error',error)}
    );
})

const stars = document.querySelectorAll('.fa-star');
const ratingValue = document.getElementById('rating-value');

stars.forEach(star => {
    star.addEventListener('click', function() {
        const value = this.getAttribute('data-value');
        ratingValue.textContent = value + ".0";  // 선택한 별점 표시

        // 모든 별을 다시 회색으로 초기화
        stars.forEach(star => star.classList.remove('star-checked'));

        // 선택한 별까지 금색으로 채움
        stars.forEach((star, index) => {
            if (index < value) {
                star.classList.add('star-checked');
            }
        });
    });
});
