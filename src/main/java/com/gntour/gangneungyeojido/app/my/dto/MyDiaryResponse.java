package com.gntour.gangneungyeojido.app.my.dto;

import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiaryFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyDiaryResponse {
    private Long diaryNo;
    private Long travelNo;
    private String diaryTitle;
    private String diaryContent;
    private List<TravelDiaryFile> diaryFiles;
}
