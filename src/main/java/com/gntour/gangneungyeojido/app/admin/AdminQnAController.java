package com.gntour.gangneungyeojido.app.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@Slf4j
public class AdminQnAController {
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] QnA 질의 리스트 조회
     */
    @GetMapping("/admin/qna")
    public String showQnAListPage(){
        return "admin/qnaList";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능(페이지 폼)] QnA 관리자로서 답변 달기
     */
    @GetMapping("/admin/qna/answer")
    public String showRegisterQnAAnswerPage(){
        return "admin/qnaAnswer";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] QnA 관리자로서 답변 달기
     * url :
     */
    public void addQnAAnswer(){}
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] QnA 삭제
     * url :
     */
    public void removeQnAAnswer(){}
}
