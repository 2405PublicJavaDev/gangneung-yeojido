package com.gntour.gangneungyeojido.domain.notice.service;

import com.gntour.gangneungyeojido.domain.notice.vo.Notice;

public interface NoticeService {
    void getAllNotices();
    void getImportantNotices();
    void getDetailNotice();
    int addNotice(Notice notice);
    void removeNotice();
    void modifyNotice();
}
