package com.gntour.gangneungyeojido.domain.mytravel.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
@Getter
@Setter
@ToString
public class TravelDiaryFile {
    private Long diaryFileNo;
    private String filePath;
    private String fileName;
    private String fileRename;
    private Long diaryNo;
    private Timestamp regDate;
    private Timestamp updateDate;
}
