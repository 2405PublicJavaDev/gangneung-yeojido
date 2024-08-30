package com.gntour.gangneungyeojido.domain.notice.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Notice {
    private Long noticeNo;
    private String noticeSubject;
    private String noticeContent;
    private String importantYn;
    private String adminId;
    private Timestamp regDate;
    private Timestamp updateDate;
}
