package com.gntour.gangneungyeojido.domain.mytravel.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
@Getter
@Setter
@ToString
public class TravelDiary {
    private Long diaryNo;
    private Long travelNo;
    private String memberId;
    private String diaryContent;
    private String diaryTitle;
    private Timestamp regDate;
    private Timestamp updateDate;
}
