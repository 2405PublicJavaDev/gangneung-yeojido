document.querySelector('#submit-btn').addEventListener('click', (e) => {
    const formTag = document.querySelector("#uploadForm");
    const formData = new FormData(formTag);
    // 파일을 FormData에 추가
    inputFileData.forEach(file => {
        formData.append('uploadFile', file);
    });
    ajax({
            url: '/myqna/register',
            method: 'post',
            payload: formData,
            isMultipart: true
        },
        (response) => {
            console.log('response', response);
        },
        (error) => {
            console.log('error',error)}
    );
});