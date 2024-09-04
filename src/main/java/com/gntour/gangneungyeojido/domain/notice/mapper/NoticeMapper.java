package com.gntour.gangneungyeojido.domain.notice.mapper;

import com.gntour.gangneungyeojido.app.notice.dto.NoticeSearchCondition;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<Notice> selectAllNotices(int currentPage, NoticeSearchCondition condition, RowBounds rowBounds);
    int selectAllNoticesCount(NoticeSearchCondition condition);
    void selectImportantNotices();
    Notice selectOneNotice(Long noticeNo);
    int insertNotice(Notice notice);
    void deleteNotice();
    void updateNotice();
}
