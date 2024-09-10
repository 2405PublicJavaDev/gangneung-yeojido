package com.gntour.gangneungyeojido.domain.qna.mapper;

import com.gntour.gangneungyeojido.app.admin.dto.QnAResponse;
import com.gntour.gangneungyeojido.app.my.dto.MyQnAResponse;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAAnswer;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface QnAMapper {

    // QnA 질문 목록 조회 (페이지네이션 적용)
    List<QnAResponse> selectAllQnA(Integer currentPage, RowBounds rowBounds);
    // 전체 QnA 수 조회 (페이지네이션을 위한 카운트)
    int selectAllQnACount();
    // 특정 QnA 번호에 따른 첨부 파일 목록 조회
    List<QnAFile> selectAllQnAFileByQnANo(Long qnaNo);
    // 특정 회원 ID에 따른 QnA 목록 조회 (마이페이지에서 사용)
    List<MyQnAResponse> selectMyQnAListByMember(String memberId);

    // 특정 QnA 번호로 QnA 정보 조회
    QnA selectQnAById(Long qnaNo);
    // 특정 QnA 번호에 따른 QnA 상세 조회 (마이페이지에서 사용)
    List<MyQnAResponse> selectOneQnADetailByQnANo(Long qnaNo);
    // 특정 QnA 번호에 따른 답변 조회
    QnAAnswer selectQnAAnswerByQnANo(Long qnaNo);


    // QnA 등록
    int insertQnA(QnA qna);
    // QnA 파일 등록
    int insertQnAFile(QnAFile qnaFile);
    // 특정 QnA 번호에 따른 QnA 삭제
    void deleteQnA(Long qnaNo);
    // 특정 QnA 번호에 따른 첨부 파일 삭제
    int deleteQnAFile(Long qnaNo);

    
    // QnA 답변 등록
    int insertQnAAnswer(QnAAnswer qnaAnswer);
    // 특정 답변 번호에 따른 답변 삭제
    void deleteQnAAnswer(Long answerNo);

}

