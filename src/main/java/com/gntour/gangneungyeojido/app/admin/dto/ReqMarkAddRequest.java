package com.gntour.gangneungyeojido.app.admin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import static com.gntour.gangneungyeojido.common.Validation.TRAVEL_NAME_VALIDATION;

@Getter
@Setter
@ToString
public class ReqMarkAddRequest {
    private Long reqMarkAddNo;
    @Pattern(regexp = TRAVEL_NAME_VALIDATION)
    private String travelName;
    @NotNull(message = "위도는 빈칸이면 안됩니다")
    private Double latitude;
    @NotNull(message = "경도는 빈칸이면 안됩니다")
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
    private Boolean isAccepted;
}
