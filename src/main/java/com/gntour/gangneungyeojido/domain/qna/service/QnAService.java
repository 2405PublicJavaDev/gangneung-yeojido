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
    int addQnA(QnA qna, List<MultipartFile> files);
    int addQnAAnswer(QnAAnswer qnaAnswer);  // 답변 추가 메서드
    void removeQnA();
    void removeQnAAnswer();


    QnA getQnAById(Long qnaNo);
    List<QnAFile> getQnAFilesByQnANo(Long qnaNo);
}
