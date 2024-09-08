package com.gntour.gangneungyeojido.app.admin;


import com.gntour.gangneungyeojido.app.admin.dto.QnAResponse;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.domain.qna.service.QnAService;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAAnswer;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminQnAController {
    private final QnAService qnaService;
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] QnA 질의 리스트 조회
     */
    @GetMapping("/admin/qna")
    public String showQnAListPage(Model model) {
        // QnA 리스트를 서비스에서 가져와서 모델에 추가
        List<QnAResponse> qnaList = qnaService.getAllQnA();
        log.info(qnaList.toString());
        model.addAttribute("qnaList", qnaList);
        return "admin/qnaList";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능(페이지 폼)] QnA 관리자로서 답변 달기
     */
    @GetMapping("/admin/qna/answer")
    public String showRegisterQnAAnswerPage(@RequestParam("qnaNo") Long qnaNo, Model model) {
        // QnA 번호에 해당하는 QnA 데이터를 가져옴
        QnA qna = qnaService.getQnAById(qnaNo);
        // QnA 번호에 해당하는 파일 목록을 가져옴
        List<QnAFile> qnaFiles = qnaService.getQnAFilesByQnANo(qnaNo);
        model.addAttribute("qna", qna);  // QnA 데이터를 모델에 추가
        model.addAttribute("qnaFiles", qnaFiles);  // 파일 목록을 모델에 추가
        return "admin/qnaAnswer";  // 답변 폼 페이지로 이동
    }


    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] QnA 관리자로서 답변 달기
     */
    @PostMapping("/admin/qna/answer")
    public String addQnAAnswer(@RequestParam("qnaNo") Long qnaNo,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               HttpSession session) {
        QnAAnswer qnaAnswer = new QnAAnswer();
        // 세션에서 관리자 ID 가져오기
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if (memberId == null) {
            log.error("관리자 ID를 찾을 수 없습니다.");
            return "redirect:/admin/qna";  // ID가 없을 경우 리스트로 리다이렉트
        }
        // 답변 객체에 값 설정
        qnaAnswer.setQnaNo(qnaNo);
        qnaAnswer.setAnswerSubject(title);
        qnaAnswer.setAnswerContent(content);
        qnaAnswer.setMemberId(memberId);
        log.info("답변 정보: " + qnaAnswer.toString());
        // 답변을 저장
        int result = qnaService.addQnAAnswer(qnaAnswer);
        if (result > 0) {
            log.info("답변 등록 성공");
        } else {
            log.error("답변 등록 실패");
        }
        return "redirect:/admin/qna";  // 답변 등록 후 리스트 페이지로 리다이렉트
    }



    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] QnA 삭제
     * url :
     */
    public void removeQnAAnswer(){}


}
