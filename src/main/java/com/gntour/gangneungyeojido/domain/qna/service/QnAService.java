package com.gntour.gangneungyeojido.domain.qna.service;

import com.gntour.gangneungyeojido.app.admin.dto.QnAResponse;
import com.gntour.gangneungyeojido.app.my.dto.MyQnAResponse;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAAnswer;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QnAService {
    // QnA 질문 리스트 출력(관리자)
    Page<QnAResponse, Void> getAllQnA(Integer currentPage);
    // 특정 QnA 번호에 따른 QnA 데이터 출력
    QnA getQnAByQnANo(Long qnaNo);
    // 특정 QnA 번호에 따른 QnAAnswer 데이터 출력
    QnAAnswer getQnAAnswerByQnANo(Long qnaNo);
    // 특정 QnA 번호에 따른 첨부된 파일을 가져옴
    List<QnAFile> getQnAFilesByQnANo(Long qnaNo);

    // QnA Answer 등록
    int addQnAAnswer(QnAAnswer qnaAnswer);
    // QnA Answer 삭제
    void removeQnAAnswer(Long answerNo);

    // 특정 회원 ID에 따른 모든 QnA+Answer 데이터 출력(답변 여부 확인용)
    List<MyQnAResponse> getAllQnAAnswerByMember(String memberIdFromSession);
    // 특정 QnA 번호에 따른 모든 QnA+Answer 데이터 출력
    List<MyQnAResponse> getOneQnADetailByQnANo(Long qnaNo);  // qnaNo로 조회하는 메서드

    // QnA 등록 (첨부파일 포함)
    int addQnA(QnA qna, List<MultipartFile> files);
    // QnA 삭제
    void removeQnA(Long qnaNo, String memberId);
}
