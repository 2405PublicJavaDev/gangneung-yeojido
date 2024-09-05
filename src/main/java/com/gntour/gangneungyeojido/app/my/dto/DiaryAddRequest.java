package com.gntour.gangneungyeojido.app.my.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryAddRequest {
    private String diaryTitle;
    private String diaryContent;
    private Long travelNo;
}
