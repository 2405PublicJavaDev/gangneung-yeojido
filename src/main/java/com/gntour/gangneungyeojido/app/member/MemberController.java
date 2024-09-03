package com.gntour.gangneungyeojido.app.member;

import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class MemberController {
    @Autowired
    private MemberService mService;

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능(페이지 폼)] 로그인, 로그아웃
     */
    @GetMapping("/login")
    public String showLoginPage(){
        return "member/login";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능(페이지 폼)] 회원가입
     */
    @GetMapping("/join")
    public String showJoinPage(){
        return "member/join";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능(페이지 폼)] 아이디 찾기
     */
    @GetMapping("/find-id")
    public String showFindMemberIdPage(){
        return "member/find-id";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능(페이지 폼)] 비밀번호 찾기
     */
    @GetMapping("/find_pw")
    public String showFindPasswordPage(){
        return "member/find-pw";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능(페이지 폼)] 회원정보 수정
     */
    public void showModifyMemberInfoPage(){}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능(페이지 폼)] 회원탈퇴
     */
    public void showRemoveMemberPage(){}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 로그인, 로그아웃
     */
    @PostMapping("/login")
    public String loginMember(String memberId, String password, HttpSession session){
        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(password);
        member = mService.loginMember(member);
        session.setAttribute(MemberUtils.MEMBER_ID, member.getMemberId());
        return "redirect:/";
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 로그인, 로그아웃
     */
    public void logoutMember(){}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 회원가입
     */
    public void joinMember(){}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 아이디 찾기
     */
    public void findMemberId(){}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 비밀번호 찾기
     */
    public void findPassword(){}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 회원정보 수정
     */
    public void modifyMemberInfo(){}

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 회원탈퇴
     */
    public void removeMember(){}

}
