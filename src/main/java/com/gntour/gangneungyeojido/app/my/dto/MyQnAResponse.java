package com.gntour.gangneungyeojido.app.my.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyQnAResponse {
    private Long qnaNo;
    private String qnaSubject;
    private String qnaContent;
    private String memberId;
    private Timestamp qnaRegDate;  // QnA 작성날짜
    private Long answerNo;
    private String answerSubject;
    private String answerContent;
    private Timestamp answerRegDate;  // QnAAnswer 작성날짜
}