package com.gntour.gangneungyeojido.domain.notice.service;

import com.gntour.gangneungyeojido.app.notice.dto.NoticeSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;

import java.util.List;

public interface NoticeService {
    /**
     * 공지사항 리스트 Service
     * (페이지네이션, 검색기능 포함)
     **/
    Page<Notice, NoticeSearchCondition> getAllNotices(Integer currentPage, NoticeSearchCondition condition);

    /**
     * 중요 공지사항 리스트 Service
     **/
    List<Notice> getImportantNotices();

    /**
     * 공지사항 상세 페이지 조회 Service
     **/
    Notice getDetailNotice(Long noticeNo);

    /**
     * 공지사항 등록 Service
     **/
    int addNotice(Notice notice);


    /**
     * 공지사항 수정 Service
     **/
    int modifyNotice(Notice notice);
}