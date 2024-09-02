package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AdminMemberController {

    @Autowired
    private MemberService mService;

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
    public String loginAdmin(@RequestParam String memberId, @RequestParam String password, HttpSession session) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(password);
        member = mService.loginMember(member);
        session.setAttribute(MemberUtils.MEMBER_ID, member.getMemberId());
        session.setAttribute(MemberUtils.MEMBER_ROLE, member.getRole());
        return "admin/black-list";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 회원 상태 조회
     */
    @GetMapping("/admin/search-member")
    public String searchMemberById(@RequestParam String memberId, Model model) {
        Member member = mService.getProfileMember(memberId);
        model.addAttribute("member", member);
        return "admin/member-status";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 회원 상태 변경
     */
    @PostMapping("/admin/modify-status")
    public String modifyMemberStatus(@ModelAttribute Member member) {
        mService.modifyMemberInfo(member);
        return "redirect:/admin/search-member?memberId=" + member.getMemberId();
    }

}
