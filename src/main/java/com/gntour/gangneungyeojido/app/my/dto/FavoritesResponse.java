package com.gntour.gangneungyeojido.app.my.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavoritesResponse {
    private Long travelNo;
    private Long favoritesNo;
    private String travelName;
    private String introduce;
    private String imageUrl;
}
