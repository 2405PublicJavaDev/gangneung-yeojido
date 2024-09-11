package com.gntour.gangneungyeojido.app.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminComplainResponse {
    private Long complainNo;
    private String category;
    private Long reviewNo;
    private String memberId;
    private Timestamp regDate;
    private Timestamp updateDate;
    private Long travelNo;
}
