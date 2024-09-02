package com.gntour.gangneungyeojido.domain.mytravel.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
@Getter
@Setter
@ToString
public class Favorites {
    private Long favoritesNo;
    private String memberId;
    private Long travelNo;
    private Timestamp regDate;
    private Timestamp updateDate;
}
