package com.gntour.gangneungyeojido.app.travel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyModifyRequest {
    private String reviewContent;
    private Long reviewNo;
    private String memberId;
}
