package com.gntour.gangneungyeojido.app.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelSearchCondition {
    private Long travelNo;
    private Long reviewNo;
    private String order;
}
