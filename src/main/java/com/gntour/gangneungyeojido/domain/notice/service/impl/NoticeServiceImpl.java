package com.gntour.gangneungyeojido.domain.notice.service.impl;

import com.gntour.gangneungyeojido.app.notice.dto.NoticeSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.notice.mapper.NoticeMapper;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;


    @Override
    public Page<Notice, NoticeSearchCondition> getAllNotices(Integer currentPage, NoticeSearchCondition condition) {
        return Page.of(currentPage, noticeMapper.selectAllNoticesCount(condition), condition, noticeMapper::selectAllNotices);
    }

    @Override
    public List<Notice> getImportantNotices() {
        return noticeMapper.selectImportantNotices();
    }

    @Override
    public Notice getDetailNotice(Long noticeNo) {
        return noticeMapper.selectOneNotice(noticeNo);
    }

    @Override
    public int addNotice(Notice notice) {
        return noticeMapper.insertNotice(notice);
    }



    @Override
    public int modifyNotice(Notice notice) {
        return noticeMapper.updateNotice(notice);

    }
}
