package com.gntour.gangneungyeojido.domain.notice.service;

import com.gntour.gangneungyeojido.app.notice.dto.NoticeSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;

public interface NoticeService {
    /**
     * 공지사항 리스트 Service
     * @return List<Notice>
    **/
    Page<Notice, NoticeSearchCondition> getAllNotices(Integer currentPage, NoticeSearchCondition condition);

    void getImportantNotices();

    /**
     * 공지사항 상세 페이지 조회 Service
     * @return Notice
     */
    Notice getDetailNotice(Long noticeNo);

    /**
     * 공지사항 등록 Service
     * @return int
     **/
    int addNotice(Notice notice);
    void removeNotice();

    /**
     * 공지사항 수정 Service
     * @return int
     **/
    int modifyNotice(Notice notice);
}
