function goBack() {
    // memberId는 HTML에서 전역 변수로 전달받음
    if (memberRole === 'ADMIN') {
        // 관리자인 경우 관리자 공지사항 리스트로 이동
        window.location.href = '/admin/notice';
    } else {
        // 일반 사용자는 이전 페이지로 되돌아감
        history.back();
    }
}