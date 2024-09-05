package com.gntour.gangneungyeojido.domain.qna.mapper;

import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnAMapper {
    void selectAllQnA();
    List<QnAFile> selectAllQnAFileByQnANo(Long qnaNo);
    void selectAllQnACount();
    void selectAllQnACountByMember();
    void selectAllQnAAnswer();
    void selectAllQnAAnswerCount();
    void selectAllQnAAnswerCountByMember();
    void insertQnA();
    int insertQnAFile(QnAFile qnaFile);
    void insertQnAAnswer();
    void deleteQnA();
    int deleteQnAFile(Long qnaNo);
    void deleteQnAAnswer();

}
