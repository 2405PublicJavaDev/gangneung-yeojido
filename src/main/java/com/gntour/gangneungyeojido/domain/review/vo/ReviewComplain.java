package com.gntour.gangneungyeojido.domain.review.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewComplain {
    private Long complainNo;
    private String category;
    private Long reviewNo;
    private String memberId;
    private Timestamp regDate;
    private Timestamp updateDate;
}
