package com.gntour.gangneungyeojido.app.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelListSearchCondition {
    private String travelName;
    private List<String> region;
    private List<String> category;
    private String memberId;
}
