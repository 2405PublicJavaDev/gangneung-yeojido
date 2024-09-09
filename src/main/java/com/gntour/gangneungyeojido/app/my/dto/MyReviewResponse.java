package com.gntour.gangneungyeojido.app.my.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewResponse {
    private Long travelNo;
    private String imageUrl;
    private String travelName;
    private String address;
    private String reviewContent;
}
