package com.gntour.gangneungyeojido.domain.travel.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import static com.gntour.gangneungyeojido.common.Validation.TRAVEL_NAME_VALIDATION;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TravelInfo {
    private Long travelNo;
    @Pattern(regexp = TRAVEL_NAME_VALIDATION)
    private String travelName;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private String address;
    private String phone;
    private String useTime;
    private String parkFee;
    private String entryFee;
    private String useFee;
    private String introduce;
    private String region;
    private String category;
    private Long zoomLevel;
    private String siteUrl;
    private String imageUrl;
    private Timestamp regDate;
    private Timestamp updateDate;
}
