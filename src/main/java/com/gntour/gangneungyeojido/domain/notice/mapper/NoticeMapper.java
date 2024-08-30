package com.gntour.gangneungyeojido.domain.notice.mapper;

import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper {
    void selectAllNotices();
    void selectAllNoticesCount();
    void selectImportantNotices();
    void selectOneNotice();
    int insertNotice(Notice notice);
    void deleteNotice();
    void updateNotice();
}
