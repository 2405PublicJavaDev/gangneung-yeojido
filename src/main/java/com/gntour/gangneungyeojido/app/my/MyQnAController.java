package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.app.my.dto.MyQnAResponse;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.domain.qna.service.QnAService;
import com.gntour.gangneungyeojido.domain.qna.vo.QnA;
import com.gntour.gangneungyeojido.domain.qna.vo.QnAFile;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyQnAController {
    private final QnAService qnaService;
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능] 문의 내역(QnA) 리스트 조회
     */
    @GetMapping("/myqna")
    public String showMyQnAListPage(HttpSession session, Model model){
        List<MyQnAResponse> myqnaList = qnaService.getAllQnAAnswerByMember(MemberUtils.getMemberIdFromSession(session));
        model.addAttribute("myqnaList", myqnaList);
        model.addAttribute("memberName", MemberUtils.getMemberIdFromSession(session));
        return "myPage/myqna-list";
    }

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능] 문의 내역(QnA) 리스트 상세 조회
     */
    @GetMapping("/myqna/{qnaNo}")
    public String showMyDetailQnAPage(@PathVariable Long qnaNo, HttpSession session, Model model){
        // 해당 QnA 디테일 정보를 가져오는 메서드 호출
        List<MyQnAResponse> qnaDetail = qnaService.getOneQnADetailByQnANo(qnaNo);
        List<QnAFile> qnaFiles = qnaService.getQnAFilesByQnANo(qnaNo);
        model.addAttribute("qnaDetail", qnaDetail);
        model.addAttribute("qnaFiles", qnaFiles);
        return "myPage/myqna-detail";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능(페이지 폼)] 문의 내역(QnA) 등록
     */
    @GetMapping("/myqna/register")
    public String showAddQnAPage(){
        return "myPage/myqna-register";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능] 문의 내역(QnA) 등록
     */
    @PostMapping("/myqna/register")
    public String addQnA(@ModelAttribute QnA qna, HttpSession session,
                         @RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFiles) {
        qna.setMemberId(MemberUtils.getMemberIdFromSession(session));
        // QnA 등록
        int result = qnaService.addQnA(qna, uploadFiles);
        // 성공적으로 등록된 경우에 디테일 페이지로 리다이렉트
        if (result > 0) {
            return "redirect:/myqna/" + qna.getQnaNo();  // 디테일 페이지로 리다이렉트
        }
        // 실패한 경우
        return "myPage/myqna-register";  // 등록 페이지로 다시 리다이렉트
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능] 문의 내역(QnA) 삭제
     */
    public void removeQnA(){};
}
