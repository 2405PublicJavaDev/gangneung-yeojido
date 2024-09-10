package com.gntour.gangneungyeojido.app.my.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryModifyRequest {
    private Long diaryNo;
    private String diaryTitle;
    private String diaryContent;
}
