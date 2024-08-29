package com.gntour.gangneungyeojido.domain.notice.mapper;

public interface NoticeMapper {
    public void selectAllNotices();
    public void selectImportantNNotices();
    public void selectOneNotice();
    public void insertNotice();
    public void deleteNotice();
    public void updateNotice();
}
