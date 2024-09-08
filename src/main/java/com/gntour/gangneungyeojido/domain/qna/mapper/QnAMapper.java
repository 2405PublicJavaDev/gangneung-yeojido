package com.gntour.gangneungyeojido.domain.qna.mapper;

import com.gntour.gangneungyeojido.app.admin.dto.QnAResponse;
import com.gntour.gangneungyeojido.app.my.dto.MyQnAResponse;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAAnswer;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnAMapper {
    List<QnAResponse> selectAllQnA();
    List<QnAFile> selectAllQnAFileByQnANo(Long qnaNo);
    List<MyQnAResponse> selectMyQnAListByMember(String memberId);

    void selectAllQnACount();
    List<QnA> selectAllQnACountByMember(String memberId);
    void selectAllQnAAnswer();
    void selectAllQnAAnswerCount();
    void selectAllQnAAnswerCountByMember();
    int insertQnA(QnA qna);
    int insertQnAFile(QnAFile qnaFile);
    int insertQnAAnswer(QnAAnswer qnaAnswer);
    void deleteQnA();
    int deleteQnAFile(Long qnaNo);
    void deleteQnAAnswer();
    QnA selectQnAById(Long qnaNo);
    List<MyQnAResponse> selectOneQnADetailByQnANo(Long qnaNo);
}
