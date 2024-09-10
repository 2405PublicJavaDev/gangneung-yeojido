package com.gntour.gangneungyeojido.app.travel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyAddRequest {
    private String reviewContent;
    private Long travelNo;
    private Long parentReviewNo;
}
