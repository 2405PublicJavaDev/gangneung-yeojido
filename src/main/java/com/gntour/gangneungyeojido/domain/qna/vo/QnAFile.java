package com.gntour.gangneungyeojido.domain.qna.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class QnAFile {
    private Long qnaFileNo;
    private String filePath;
    private String fileName;
    private String fileRename;
    private Long qnaNo;
    private Timestamp regDate;
    private Timestamp updateDate;
    private String webPath;
    private Long fileOrder;
}
