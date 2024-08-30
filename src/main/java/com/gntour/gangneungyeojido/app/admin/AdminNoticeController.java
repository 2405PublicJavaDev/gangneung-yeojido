package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.common.MemberRole;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminNoticeController {
    private final NoticeService noticeService;

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능(페이지 폼)] 공지사항 등록
     */
    @GetMapping("/admin/noticeRegister")
    public String showRegisterNoticePage(){
        return "admin/noticeRegister";
    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능(페이지 폼)] 공지사항 수정
     */
    @GetMapping("/admin/noticeUpdate")
    public String showUpdateNoticePage(){
        return "admin/noticeUpdate";
    }

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] 공지사항 등록
     */
    @PostMapping("/admin/noticeRegister")
    public String addNotice(HttpSession session, Notice notice){
        MemberRole role = MemberUtils.getMemberRoleFromSession(session);
        if(role == MemberRole.ADMIN){
            notice.setAdminId(MemberUtils.getMemberIdFromSession(session));
            notice.setImportantYn("N"); // TODO 적절하게 변경하기
            int result = noticeService.addNotice(notice);
            return "redirect:/notice/noticeList"; // TODO redirect 정하기
        } else {
            throw new RuntimeException(); // TODO 적절한 예외 처리로 바꾸기
        }

    }
    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [관리자 기능] 공지사항 수정
     */
    public void modifyNotice(){}

}
