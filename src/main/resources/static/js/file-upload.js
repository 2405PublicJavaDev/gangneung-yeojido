let maxWidth = 1024;  // 최대 너비
let maxHeight = 768; // 최대 높이
let maxFileCount = 5;
const inputFileData = [];
const inputFile = document.querySelector('.file-upload input[type=file]');
document.querySelector('#uploadBtn').addEventListener('click', (e) => {
    if(inputFileData.length >= maxFileCount) {
        maxFileAlert();
    } else {
        inputFile.click();
    }
});
inputFile.addEventListener('change', function(event) {
    const files = event.target.files;
    // 파일이 선택되었는지 확인
    if (files.length > 0) {
        if(files.length + inputFileData.length <= maxFileCount) {
            Array.from(files).forEach(file => {
                console.log(file.type);
                if (file && file.type.startsWith('image/')) {
                    // 이미지를 로드해 크기 제한 검사
                    const img = new Image();
                    const reader = new FileReader();

                    reader.onload = function(readerEvent) {
                        img.src = readerEvent.target.result;

                        img.onload = function() {
                            // 이미지 크기 검사
                            if (img.width > maxWidth || img.height > maxHeight) {
                                imageSizeAlert();
                            } else {
                                // 제한에 맞는 경우에만 inputFileData에 추가
                                file.result = readerEvent.target.result;
                                inputFileData.push(file);
                                thumbnailUpdate();
                            }
                        };
                    };
                    reader.readAsDataURL(file);  // 파일을 읽어 이미지 로드
                } else {
                    onlyImageAlert();
                }
            });
            thumbnailUpdate();
        } else {
            maxFileAlert();
        }
    }
});
// 크기 제한 경고 함수
const imageSizeAlert = () => {
    alert(`이미지 크기는 최대 ${maxWidth}x${maxHeight} 이어야 합니다.`);
};
const onlyImageAlert = () => {
    alert('이미지 파일만 선택해주세요.');
}
const maxFileAlert = () => {
    alert(`파일은 최대 ${maxFileCount}개까지 추가 가능합니다.`);
}
const thumbnailUpdate = () => {
    const thumbnailRoot = document.querySelector('#file-thumbnail-root');
    thumbnailRoot.replaceChildren();
    Promise.all(inputFileData.filter((file) => !(file.result)).map((file) => {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = function (e) {
                // 이미지 미리보기 생성
                file.result = e.target.result; // 다시 로딩 안할려고 이렇게 함
                resolve(true);
            };
            reader.onerror = function(e) {
                reject();
            }

            reader.readAsDataURL(file);
        });
    })).then(() => {
        inputFileData.forEach((file, index) => {
            const imgWrapper = document.createElement('div');
            imgWrapper.classList.add('file-thumbnail-wrapper');

            const img = document.createElement('img');
            img.classList.add('thumbnail');
            img.src = file.result;
            img.addEventListener('click', (clickEvent) => {
                const dialog = document.querySelector('dialog');
                const dialogImg = document.querySelector('dialog>img')
                dialogImg.src = file.result;
                dialog.showModal();
                dialog.addEventListener('click', (dialogClickEvent) => {
                    dialog.close();
                })
            })
            const xBtn = document.createElement('img')
            xBtn.classList.add('x-btn');
            xBtn.src = '/img/x_button.png';
            xBtn.addEventListener('click', (xBtnClickEvent) => {
                inputFileData.splice(index, 1);
                thumbnailUpdate();
            })
            imgWrapper.appendChild(xBtn);
            imgWrapper.appendChild(img);
            thumbnailRoot.appendChild(imgWrapper);
        })
    }).catch((error) => {
        console.log(error);
    })
}