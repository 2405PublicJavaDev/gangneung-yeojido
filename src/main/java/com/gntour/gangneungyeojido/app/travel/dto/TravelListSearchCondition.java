package com.gntour.gangneungyeojido.app.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelListSearchCondition {
    private String travelName;
    private String region;
    private String category;
}
