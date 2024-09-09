package com.gntour.gangneungyeojido.domain.review.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Review {
    private Long reviewNo;
    private Double score;
    private String reviewContent;
    private Long parentReviewNo;
    private Long travelNo;
    private String memberId;
    private Timestamp regDate;
    private Timestamp updateDate;
}
