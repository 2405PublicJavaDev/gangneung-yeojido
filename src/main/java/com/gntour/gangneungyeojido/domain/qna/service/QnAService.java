package com.gntour.gangneungyeojido.domain.qna.service;

import com.gntour.gangneungyeojido.app.admin.dto.QnAResponse;
import com.gntour.gangneungyeojido.app.my.dto.MyQnAResponse;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAAnswer;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QnAService {
    List<QnAResponse> getAllQnA();
    QnA getAllQnAByMember(Long qnaNoLong);
    QnA getAllQnAByMember();
    List<MyQnAResponse> getAllQnAAnswerByMember(String memberIdFromSession);
    List<MyQnAResponse> getOneQnADetailByQnANo(Long qnaNo);  // qnaNo로 조회하는 메서드
    int addQnA(QnA qna, List<MultipartFile> files);
    int addQnAAnswer(QnAAnswer qnaAnswer);  // 답변 추가 메서드
    void removeQnA();
    void removeQnAAnswer(Long answerNo);  // 답변 삭제 메서드

    QnA getQnAById(Long qnaNo);
    List<QnAFile> getQnAFilesByQnANo(Long qnaNo);
    QnAAnswer getQnAAnswerByQnANo(Long qnaNo);  // QnA 번호로 답변 조회 메서드
}
