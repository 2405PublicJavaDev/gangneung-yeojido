package com.gntour.gangneungyeojido.app.travel.dto;

import com.gntour.gangneungyeojido.domain.review.vo.ReviewFile;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewResponse {
    private Long reviewNo;
    private Double score;
    private String reviewContent;
    private Long parentReviewNo; // Nullable
    private Long travelNo;
    private String memberId;
    private Timestamp regDate;
    private Timestamp updateDate;
    private List<ReviewFile> reviewFiles; // List of related files
    private Long replyCnt;
}
