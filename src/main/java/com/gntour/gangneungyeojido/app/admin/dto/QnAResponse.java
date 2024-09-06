package com.gntour.gangneungyeojido.app.admin.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnAResponse {
    private Long qnaNo;
    private String qnaSubject;
    private String memberId;
    private Timestamp regDate;
    private Long qnaAnswerCount;
}
