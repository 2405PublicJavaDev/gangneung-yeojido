package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.app.admin.dto.LoginRequest;
import com.gntour.gangneungyeojido.common.MemberRole;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService mService;

    private final ReviewService rService;
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
    public String showReportListPage(Model model, @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage) {
        model.addAttribute("page", rService.getAllComplainReviews(currentPage));
        return "admin/report-list";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [관리자 기능] 블랙리스트 조회
     */
    @GetMapping("/admin/black-list")
    public String showBlackListPage(Model model, @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage) {
//        List<Member> blackList = mService.getAllBlackListMember(currentPage);
        model.addAttribute("page", mService.getAllBlackListMember(currentPage));
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
    @ResponseBody
    public EmptyResponse loginAdmin(@RequestBody @Valid LoginRequest loginRequest, HttpSession session) {
        Member member = new Member();
        member.setMemberId(loginRequest.getMemberId());
        member.setPassword(loginRequest.getPassword());
        member = mService.loginMember(member);
        if(MemberRole.valueOf(member.getRole()) != MemberRole.ADMIN) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        session.setAttribute(MemberUtils.MEMBER_ID, member.getMemberId());
        session.setAttribute(MemberUtils.MEMBER_ROLE, member.getRole());
        return new EmptyResponse();
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
