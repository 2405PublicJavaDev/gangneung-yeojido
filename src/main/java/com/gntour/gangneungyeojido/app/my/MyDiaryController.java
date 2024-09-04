package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.mytravel.service.TravelDiaryService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyDiaryController {
    private final TravelDiaryService travelDiaryService;

    /**
     * 담당자 : 백인호님
     * 관련 기능 :[마이페이지 기능] 나의 여행 기록 리스트 조회
     *
     */
    @GetMapping("/diary")
    public String showMyDiariesPage(HttpSession session, Model model){
        List<TravelDiary> travelDiaryList = travelDiaryService.getAllDiariesByMember(MemberUtils.getMemberIdFromSession(session));
        model.addAttribute("travelDiaryList", travelDiaryList);
        return "/myPage/myDiary";
    };

    /**
     * 담당지 : 백인호님
     * 관련 가눙 : [마이페이지 기능] 나의 여행 기록 상세 조회
     */
    @GetMapping("/diary-detail")
    public String showMyDetailDiaryPage(HttpSession session, Model model){
        TravelDiary travelDiary = travelDiaryService.getDetailDiaryByMember(MemberUtils.getMemberIdFromSession(session));
        model.addAttribute("travelDiary", travelDiary);
        return "/myPage/myDiaryDetail";
    };

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능(페이지 폼)] 나의 여행 기록 등록
     */
    @GetMapping("/register-diary")
    public String showAddMyDiaryPage(){
        return "/myPage/register-myDiary";
    };

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 여행 기록 등록
     */
    @PostMapping("/register-diary")
    public String addMyDiaryPage(HttpSession session, Model model, MultipartFile uploadFile, TravelDiary travelDiary){
        int result = travelDiaryService.addDiary(travelDiary,uploadFile,MemberUtils.getMemberIdFromSession(session));
        return "redirect:/register-diary";
    };
    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능(페이지 폼)] 나의 여행 기록 수정
     */
    @GetMapping("/modify-diary")
    public String showUpdateMyDiaryPage(){
        return "/myPage/modify-myDiary";
    };


    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 여행 기록 수정
     */
    @PostMapping("/modify-diary")
    public String modifyDiaryPage(){
        return null;
    };

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 여행 기록 등록
     */
    public void addDiary(){};

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 여행 기록 수정
     */
    public void modifyDiary(){};

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 여행 기록 삭제
     */
    @GetMapping("/remove")
    public String removeDiary(HttpSession session){
        int result = travelDiaryService.removeDiary(MemberUtils.getMemberIdFromSession(session));
        return "redirect:/diary";
    };


}
