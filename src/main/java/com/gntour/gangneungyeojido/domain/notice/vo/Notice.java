package com.gntour.gangneungyeojido.domain.notice.vo;

import java.sql.Time;
import java.sql.Timestamp;

public class Notice {
    private Long noticeNo;
    private String noticeSubject;
    private String noticeContent;
    private String importantYn;
    private String adminId;
    private Timestamp regDate;
    private Timestamp updateDate;
}
