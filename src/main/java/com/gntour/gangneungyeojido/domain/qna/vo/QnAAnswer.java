package com.gntour.gangneungyeojido.domain.qna.vo;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class QnAAnswer {
    private Long answerNo;
    private String answerSubject;
    private String answerContent;
    private Long qnaNo;
    private String memberId;
    private Timestamp regDate;
    private Timestamp updateDate;
}
