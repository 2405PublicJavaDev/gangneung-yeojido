package com.gntour.gangneungyeojido.domain.notice.service;

import com.gntour.gangneungyeojido.app.notice.dto.NoticeSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;

import java.util.List;

public interface NoticeService {
    /**
     * 공지사항 리스트 Service
     * @return List<Notice>
    **/
    Page<Notice, NoticeSearchCondition> getAllNotices(Integer currentPage, NoticeSearchCondition condition);
    void getImportantNotices();
    /**
     * 공지사항 상세 페이지 조회 Service
     * return
     */
    Notice getDetailNotice(Long noticeNo);
    int addNotice(Notice notice);
    void removeNotice();
    void modifyNotice();
}
