package com.gntour.gangneungyeojido.domain.travel.vo;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqMarkAdd {
    private Long reqMarkAddNo;
    private String travelName;
    private String address;
    private String phone;
    private String useTime;
    private String parkFee;
    private String entryFee;
    private String useFee;
    private String introduce;
    private String acceptableStatus;
    private String siteUrl;
    private String imageUrl;
    private String memberId;
    private Timestamp regDate;
    private Timestamp updateDate;
}
