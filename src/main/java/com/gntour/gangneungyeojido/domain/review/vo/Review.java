package com.gntour.gangneungyeojido.domain.review.vo;

import java.sql.Timestamp;

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
