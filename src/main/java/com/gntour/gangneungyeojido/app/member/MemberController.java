package com.gntour.gangneungyeojido.app.member;


import com.gntour.gangneungyeojido.app.admin.dto.LoginRequest;
import com.gntour.gangneungyeojido.app.member.dto.JoinRequest;
import com.gntour.gangneungyeojido.common.MemberRole;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService mService;

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
    @ResponseBody
    public EmptyResponse loginMember(@RequestBody @Valid LoginRequest loginRequest, HttpSession session){
        Member member = new Member();
        member.setMemberId(loginRequest.getMemberId());
        member.setPassword(loginRequest.getPassword());
        member = mService.loginMember(member);
        session.setAttribute(MemberUtils.MEMBER_ID, member.getMemberId());
        session.setAttribute(MemberUtils.MEMBER_ROLE, member.getRole());
        return new EmptyResponse();
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 로그인, 로그아웃
     */

    public void logoutMember(){
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 회원가입
     */
    @PostMapping("/join")
    @ResponseBody
    public EmptyResponse joinMember(@RequestBody @Valid JoinRequest joinRequest){
        Member member = new Member();
        member.setMemberId(joinRequest.getMemberId());
        member.setPassword(joinRequest.getPassword());
        member.setName(joinRequest.getName());
        member.setBirthDate(joinRequest.getBirthDate());
        member.setEmail(joinRequest.getEmail());
        member.setPhone(joinRequest.getPhone());
        member.setRole("MEMBER");
        if(!joinRequest.getPassword().equals(joinRequest.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.PW_PW_CHECK_NOT_MATCH);
        }
        int result = mService.joinMember(member);

        return new EmptyResponse();
    }

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
