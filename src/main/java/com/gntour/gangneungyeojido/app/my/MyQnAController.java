package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.app.admin.dto.QnAResponse;
import com.gntour.gangneungyeojido.app.member.dto.AddQnAResponse;
import com.gntour.gangneungyeojido.app.my.dto.MyQnAResponse;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
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
    private final NoticeService noticeService;
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능] 문의 내역(QnA) 리스트 조회
     */
    @GetMapping("/myqna")
    public String showMyQnAListPage(HttpSession session, Model model){
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null){
            return "redirect:/login";
        }
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
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null){
            return "redirect:/login";
        }
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
    public String showAddQnAPage(HttpSession session){
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null){
            return "redirect:/login";
        }
        return "myPage/myqna-register";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능] 문의 내역(QnA) 등록
     */
    @PostMapping("/myqna/register")
    @ResponseBody
    public AddQnAResponse addQnA(@ModelAttribute QnA qna, HttpSession session,
                                 @RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFiles) {
        qna.setMemberId(MemberUtils.getMemberIdFromSession(session));
        int result = qnaService.addQnA(qna, uploadFiles);
        AddQnAResponse res = new AddQnAResponse();
        if (result > 0) {
            res.setQnaNo(qna.getQnaNo());
        } else {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return res;
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [마이페이지 기능] 문의 내역(QnA) 삭제
     */
    @PostMapping("/myqna/{qnaNo}")
    public String removeQnA(@PathVariable Long qnaNo, HttpSession session) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        // QnA 삭제 (답변과 첨부파일도 함께 삭제)
        qnaService.removeQnA(qnaNo, memberId);
        return "redirect:/myqna";  // 삭제 후 목록 페이지로 리다이렉트
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [푸터 기능] 주요 공지사항 리스트 조회
     */
    @ModelAttribute
    public String getImportantNoticeList(Model model){
        List<Notice> importantNotices = noticeService.getImportantNotices();
        model.addAttribute("footerImportantNotices", importantNotices);
        return "fragments/footer";
    }

}
