package com.gntour.gangneungyeojido.domain.qna.service.impl;

import com.gntour.gangneungyeojido.app.admin.dto.QnAResponse;
import com.gntour.gangneungyeojido.app.my.dto.MyQnAResponse;
import com.gntour.gangneungyeojido.common.FileUtil;
import com.gntour.gangneungyeojido.common.UploadCategory;
import com.gntour.gangneungyeojido.domain.qna.mapper.QnAMapper;
import com.gntour.gangneungyeojido.domain.qna.service.QnAService;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAAnswer;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAServiceImpl implements QnAService {
    private final QnAMapper qnaMapper;
    private final FileUtil fileUtil;

    @Override
    public List<QnAResponse> getAllQnA() {
        return qnaMapper.selectAllQnA();
    }

    @Override
    public QnA getAllQnAByMember(Long qnaNo) {
        return qnaMapper.selectQnAById(qnaNo);
    }

    @Override
    public QnA getAllQnAByMember() {
        return null;
    }

    @Override
    public List<MyQnAResponse> getAllQnAAnswerByMember(String memberId) {
        return qnaMapper.selectMyQnAListByMember(memberId);  // 새로운 쿼리로 수정
    }

    @Override
    public List<MyQnAResponse> getOneQnADetailByQnANo(Long qnaNo) {
        return qnaMapper.selectOneQnADetailByQnANo(qnaNo);  // 매퍼 쿼리 호출

    }

    @Override
    public QnA getQnAById(Long qnaNo) {
        return qnaMapper.selectQnAById(qnaNo);  // 매퍼에서 데이터 조회
    }
    @Override
    public int addQnA(QnA qna, List<MultipartFile> files) {
        int result = qnaMapper.insertQnA(qna);
        try {
            result += fileUtil.uploadFiles(UploadCategory.QNA, files, qna.getQnaNo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int addQnAAnswer(QnAAnswer qnaAnswer) {
        return qnaMapper.insertQnAAnswer(qnaAnswer);
    }
    @Override
    public void removeQnA() {
    }

    @Override
    public void removeQnAAnswer(Long answerNo) {
        qnaMapper.deleteQnAAnswer(answerNo);  // 매퍼를 통해 답변 삭제 쿼리 실행
    }

    @Override
    public QnAAnswer getQnAAnswerByQnANo(Long qnaNo) {
        return qnaMapper.selectQnAAnswerByQnANo(qnaNo);  // QnA 번호로 답변 조회
    }
    @Override
    public List<QnAFile> getQnAFilesByQnANo(Long qnaNo) {
        return qnaMapper.selectAllQnAFileByQnANo(qnaNo);
    }

}
