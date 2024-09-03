document.addEventListener('DOMContentLoaded', function() {
    const icons = document.querySelectorAll('.fa-bookmark');

    icons.forEach(function(icon) {
        icon.addEventListener('click', function() {
            // 클릭한 아이콘에 active 클래스를 토글
            this.classList.toggle('active');
        });
    });
});