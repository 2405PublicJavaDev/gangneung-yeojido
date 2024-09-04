const ajax = (config, onSuccess, onError) => {
    const validateForm = (errors) => {
        // 모든 요소를 선택합니다.
        const allElements = document.querySelectorAll('*');

        // 선택된 각 요소에 대해 `common-input-error` 클래스를 제거합니다.
        allElements.forEach(element => {
            element.title = "";
            element.classList.remove('common-input-error');
        });
        let minTop = 987654321;
        let firstErrorInput = null;
        errors.forEach((error) => {
            const input = document.querySelector(`input[name=${error.field}]`);
            input.classList.add('common-input-error');
            const offset = getOffset(input);
            if(offset.top < minTop)  {
                minTop = offset.top;
                firstErrorInput = input;
            }
            input.title = error.reason;
        });
        if (firstErrorInput) {
            firstErrorInput.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    }

    const getOffset = (el) => {
        const rect = el.getBoundingClientRect();
        return {
            left: rect.left + window.scrollX,
            top: rect.top + window.scrollY
        };
    }

    let requestPayload;

    // Check if the form is for file uploads (multipart/form-data)
    if (config.isMultipart) {
        // Do not use JSON.stringify, use the raw FormData
        requestPayload = config.payload; // payload is a FormData object
    } else {
        // Handle regular JSON payloads
        requestPayload = JSON.stringify(config.payload);
    }
    fetch(config.url, {
        method: config.method,
        headers: config.isMultipart ? {
        } : {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: requestPayload
    }).then(async (response) => {
        if (response.ok) {
            return await response.json();
        } else {
            // 에러 응답을 JSON으로 변환하고 전달
            throw await response.json();
        }
    })
        .then((data) => onSuccess(data))
        .catch((error) => {
            if(error.code === 'C001') {
                validateForm(error.errors);
            }
            onError(error);
        });
}
// 아래는 global 한 이벤트를 설정하는 것으로
// 버그가 발생하지 않는 한 크게 신경쓰지 않으셔도 됩니다.
let myExpandVisible = false;
window.addEventListener('click', (e) => {
    if(e.target !== document.querySelector("#header-my") && document.querySelector(".header-my-expand") && !document.querySelector(".header-my-expand").contains(e.target)) {
        document.querySelector(".header-my-expand").style.display = "none";
        myExpandVisible = false;
    }
}, true);

document.querySelectorAll('input').forEach(input => {
    input.addEventListener('input', (e) => {
        input.classList.remove('common-input-error');
    }, true);
})