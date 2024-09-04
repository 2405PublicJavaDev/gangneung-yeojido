document.addEventListener('DOMContentLoaded', function() {
    document.querySelector(".travels").style.opacity = '1';
    document.querySelector(".travels").style.transform = 'translateY(0)';

    const icons = document.querySelectorAll('.fa-bookmark');

    icons.forEach(function(icon) {
        icon.addEventListener('click', function() {
            // 클릭한 아이콘에 active 클래스를 토글
            this.classList.toggle('active');
        });
    });
});