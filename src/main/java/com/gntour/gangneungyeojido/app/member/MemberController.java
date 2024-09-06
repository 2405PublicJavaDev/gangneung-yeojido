package com.gntour.gangneungyeojido.app.member;


import com.gntour.gangneungyeojido.app.admin.dto.LoginRequest;
import com.gntour.gangneungyeojido.app.member.dto.*;
import com.gntour.gangneungyeojido.common.MemberRole;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.VerificationCodeGenerator;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.email.service.EmailValidService;
import com.gntour.gangneungyeojido.domain.email.vo.EmailMessage;
import com.gntour.gangneungyeojido.domain.email.service.EmailService;
import com.gntour.gangneungyeojido.domain.email.vo.EmailValid;
import com.gntour.gangneungyeojido.domain.member.service.MemberService;
import com.gntour.gangneungyeojido.domain.member.vo.Member;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService mService;
    private final EmailService emailService;
    private final EmailValidService emailValidService;
    private final NoticeService noticeService;

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
    @GetMapping("/find-pw")
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
    @PostMapping("/logout")
    @ResponseBody
    public EmptyResponse logoutMember(HttpSession session){
        session.invalidate();
        return new EmptyResponse();
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 회원가입
     */
    @PostMapping("/join")
    @ResponseBody
    public EmptyResponse joinMember(@RequestBody @Valid JoinRequest joinRequest){
        if(!joinRequest.getPassword().equals(joinRequest.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.PW_PW_CHECK_NOT_MATCH);
        }
        if(!emailValidService.isValidEmail(joinRequest.getEmail(), joinRequest.getAuth())) {
            throw new BusinessException(ErrorCode.EMAIL_VALID_FAIL);
        }
        Member member = new Member();
        member.setMemberId(joinRequest.getMemberId());
        member.setPassword(joinRequest.getPassword());
        member.setName(joinRequest.getName());
        member.setBirthDate(convertStringToTimestamp(joinRequest.getBirthDate()));
        member.setEmail(joinRequest.getEmail());
        member.setPhone(joinRequest.getPhone());
        member.setRole(MemberRole.MEMBER.toString());
        int result = mService.joinMember(member);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }

    private Timestamp convertStringToTimestamp(String str) {
        // "yyyyMMdd" 형식의 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // LocalDate로 변환
        LocalDate localDate = LocalDate.parse(str, formatter);

        // LocalDate를 Timestamp로 변환
        return Timestamp.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     *  담당자 : 이경학님
     *  관련기능 : [회원관리 기능] 아이디 찾기
     */
    @PostMapping("/find-id")
    @ResponseBody
    public FindIdResponse findMemberId(@RequestBody @Valid FindIdRequest findIdRequest, Model model){
        Member member = new Member();
        member.setName(findIdRequest.getName());
        member.setBirthDate(convertStringToTimestamp(findIdRequest.getBirthDate()));
        member = mService.findMemberId(member);
        log.info(member.toString());
        return new FindIdResponse(member.getMemberId());
    }

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

    /**
     * 담당자 : 이경학님
     * 관련기능 : [회원관리 기능] 인증번호 보내기
     */
    @PostMapping("/send-check-code")
    @ResponseBody
    public EmptyResponse sendCheckCode(@RequestBody @Valid SendCheckCodeRequest req) {
        String validCode = VerificationCodeGenerator.generateVerificationCode();
        emailService.sendMail(new EmailMessage(
                req.getEmail(),
                "[강릉여지도] 강릉여지도계정 가입 인증번호", // TODO 제목 지정하기
                validCode // TODO 내용 지정하기
        ));
        emailValidService.addOrUpdateValidCode(req.getEmail(), validCode);
        return new EmptyResponse();
    }

    /**
     * 담당자 : 이경학님
     * 관련기능 : [회원관리 기능] 인증번호 확인
     */
    @PostMapping("/valid-check-code")
    @ResponseBody
    public EmptyResponse validCheckCode(@RequestBody @Valid ValidCheckCodeRequest req) {
        boolean isValid = emailValidService.isValidEmail(req.getEmail(),req.getAuth());
        if(!isValid) {
            throw  new BusinessException(ErrorCode.EMAIL_VALID_FAIL);
        }
        return new EmptyResponse();
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
