package com.gntour.gangneungyeojido.domain.review.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewFile {
    private Long reviewFileNo;
    private String filePath;
    private String fileName;
    private String fileRename;
    private Long reviewNo;
    private Timestamp regDate;
    private Timestamp updateDate;
}
