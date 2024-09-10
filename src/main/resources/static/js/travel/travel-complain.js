document.addEventListener("DOMContentLoaded", function () {
    const reasons = document.querySelectorAll(".complain-reason");
    const submitButton = document.querySelector("#submit-review");
    const form = document.getElementById("complain-form");

    reasons.forEach(reason => {
        reason.addEventListener("click", function () {
            // 모든 요소에서 .selected 클래스를 제거
            reasons.forEach(r => r.classList.remove("selected"));
            // 클릭된 요소에 .selected 클래스 추가
            this.classList.add("selected");
            // 선택한 신고 사유를 hidden input에 저장
            const selectedValue = this.getAttribute('data-value');
            document.querySelector('input[name="complainKeyword"]').value = selectedValue;
            // 선택된 값 콘솔에 출력
            // console.log(selectedValue);
        });
    });
    submitButton.addEventListener("click", function () {
        const selectedReason = document.querySelector('input[name="complainKeyword"]').value;
        if (!selectedReason) {
            alert("신고 사유를 선택해주세요.");
            return;
        }
        if (confirm("선택한 사유로 리뷰를 신고하시겠습니까?")) {
            form.submit();
        } else {
            alert("신고가 취소되었습니다.");
        }
    });
});

function goBack() {
    if(confirm("이전 화면으로 이동합니다.")) {
        history.back();
    }
}