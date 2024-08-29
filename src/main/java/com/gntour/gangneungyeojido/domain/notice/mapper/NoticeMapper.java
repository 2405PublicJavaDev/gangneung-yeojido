package com.gntour.gangneungyeojido.domain.notice.mapper;

public interface NoticeMapper {
    void selectAllNotices();
    void selectAllNoticesCount();
    void selectImportantNNotices();
    void selectOneNotice();
    void insertNotice();
    void deleteNotice();
    void updateNotice();
}
