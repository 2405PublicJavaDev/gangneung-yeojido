package com.gntour.gangneungyeojido.domain.notice.domain;

import java.sql.Timestamp;

public class Notice {
    private Long noticeNo;
    private String noticeSubject;
    private String noticeContent;
    private Timestamp regDate;
    private String importantYn;
    private String adminId;
}
