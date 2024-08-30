package com.gntour.gangneungyeojido.domain.mytravel.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
@Getter
@Setter
public class TravelDiary {
    private Long diaryNo;
    private Long travelNo;
    private String memberId;
    private String diaryContent;
    private Timestamp regDate;
    private Timestamp updateDate;
}
