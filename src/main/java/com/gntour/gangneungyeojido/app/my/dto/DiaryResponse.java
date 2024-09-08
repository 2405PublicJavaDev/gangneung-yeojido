package com.gntour.gangneungyeojido.app.my.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiaryResponse {
    private Long diaryNo;
    private Long travelNo;
    private String travelName;
    private String diaryContent;
}
