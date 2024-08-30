package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class AdminMemberController {
//    @Autowired
//    private MemberService mService;

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 관리자 로그인
     */
    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        log.info("showAdminLoginPage");
        return "admin/login";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 신고리스트 조회
     */
    @GetMapping("/admin/report-list")
    public String showReportListPage() {
        return "admin/report-list";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 블랙리스트 조회
     */
    @GetMapping("/admin/black-list")
    public String showBlackListPage() {
        return "admin/black-list";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능(페이지 폼)] 회원 상태 변경, [관리자 기능] 회원 상태 조회
     */
    @GetMapping("/admin/member-status")
    public String showMemberStatusPage() {
        return "admin/member-status";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 관리자 로그인
     */
    @PostMapping("/admin/login")
    public void loginAdmin() {}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 회원 상태 조회
     */
    public void searchMemberById() {}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 회원 상태 변경
     */
    public void modifyMemberStatus() {}
}
