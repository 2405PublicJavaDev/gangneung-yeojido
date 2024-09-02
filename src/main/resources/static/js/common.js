let myExpandVisible = false;
window.addEventListener('click', (e) => {
    if(e.target !== document.querySelector("#header-my") && document.querySelector(".header-my-expand") && !document.querySelector(".header-my-expand").contains(e.target)) {
        e.preventDefault();
        document.querySelector(".header-my-expand").style.display = "none";
        myExpandVisible = false;
    }
}, true);

const ajax = (config, onSuccess, onError) => {
    fetch(config.url, {
        method: config.method,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(config.payload)
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
                validateForm(error.errors.map(e => e.field));
            }
            onError(error);
        });
}

const validateForm = (fields) => {
    console.log(fields);
    // 모든 요소를 선택합니다.
    const allElements = document.querySelectorAll('*');

    // 선택된 각 요소에 대해 `common-input-error` 클래스를 제거합니다.
    allElements.forEach(element => {
        element.classList.remove('common-input-error');
    });
    let minTop = 987654321;
    let firstErrorInput = null;
    fields.forEach((field) => {
        const input = document.querySelector(`input[name=${field}]`);
        input.classList.add('common-input-error');
        const offsetTop = getOffset(input).top;
        if(offsetTop < minTop)  {
            minTop = offsetTop;
            firstErrorInput = input;
        }
    });
    if (firstErrorInput) {
        firstErrorInput.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}

function getOffset(el) {
    const rect = el.getBoundingClientRect();
    return {
        left: rect.left + window.scrollX,
        top: rect.top + window.scrollY
    };
}