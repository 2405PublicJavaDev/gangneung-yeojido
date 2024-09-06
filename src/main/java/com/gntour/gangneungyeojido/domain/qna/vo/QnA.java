package com.gntour.gangneungyeojido.domain.qna.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class QnA {
    private Long qnaNo;
    private String qnaSubject;
    private String qnaContent;
    private String memberId;
    private Timestamp regDate;
    private Timestamp updateDate;
}
