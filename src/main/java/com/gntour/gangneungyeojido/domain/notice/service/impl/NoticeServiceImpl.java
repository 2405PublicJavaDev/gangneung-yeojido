package com.gntour.gangneungyeojido.domain.notice.service.impl;

import com.gntour.gangneungyeojido.domain.notice.mapper.NoticeMapper;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Override
    public void getAllNotices() {

    }

    @Override
    public void getImportantNotices() {

    }

    @Override
    public void getDetailNotice() {

    }

    @Override
    public int addNotice(Notice notice) {
        return noticeMapper.insertNotice(notice);
    }

    @Override
    public void removeNotice() {

    }

    @Override
    public void modifyNotice() {

    }
}
