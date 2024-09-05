package com.gntour.gangneungyeojido.app.my.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesResponse {
    private Long travelNo;
    private Long favoritesNo;
    private String travelName;
    private String introduce;
}
