package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.app.my.dto.DiaryAddRequest;
import com.gntour.gangneungyeojido.app.my.dto.DiaryModifyRequest;
import com.gntour.gangneungyeojido.app.my.dto.MyDiaryResponse;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.mytravel.service.TravelDiaryService;
import com.gntour.gangneungyeojido.domain.mytravel.vo.Favorites;
import com.gntour.gangneungyeojido.domain.mytravel.vo.TravelDiary;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
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
    public String showMyDiariesPage(HttpSession session, Model model, @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage){
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        Page<MyDiaryResponse, String> travelDiaryList = travelDiaryService.getAllDiariesByMember(currentPage, memberId);
        model.addAttribute("page", travelDiaryList);
        return "/myPage/myDiary";
    };

    /**
     * 담당지 : 백인호님
     * 관련 가눙 : [마이페이지 기능] 나의 여행 기록 상세 조회
     */
    @GetMapping("/diary-detail/{diaryNo}")
    public String showMyDetailDiaryPage(@PathVariable int diaryNo, HttpSession session, Model model){
        MyDiaryResponse travelDiary = travelDiaryService.getDetailDiaryByMember(diaryNo,MemberUtils.getMemberIdFromSession(session));
        model.addAttribute("travelDiary", travelDiary);
        return "myPage/myDiaryDetail";
    }

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
    @ResponseBody
    public EmptyResponse addDiary(
            HttpSession session,
            Model model,
            @ModelAttribute @Valid DiaryAddRequest diaryAddRequest,
            @RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFiles
    ){
        TravelDiary travelDiary = new TravelDiary();
        travelDiary.setTravelNo(diaryAddRequest.getTravelNo());
        travelDiary.setDiaryTitle(diaryAddRequest.getDiaryTitle());
        travelDiary.setDiaryContent(diaryAddRequest.getDiaryContent());
        travelDiary.setMemberId(MemberUtils.getMemberIdFromSession(session));
        int result = travelDiaryService.addDiary(travelDiary, uploadFiles);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    };
    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능(페이지 폼)] 나의 여행 기록 수정
     */
    @GetMapping("/modify-diary/{diaryNo}")
    public String showUpdateMyDiaryPage(
            @PathVariable int diaryNo,
            HttpSession session,
            Model model
    ){
        MyDiaryResponse travelDiary = travelDiaryService.getDetailDiaryByMember(diaryNo,MemberUtils.getMemberIdFromSession(session));
        model.addAttribute("travelDiary", travelDiary);
        return "/myPage/modify-myDiary";
    };


    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 여행 기록 수정
     */
    @PostMapping("/modify-diary")
    public String modifyDiary(
            @ModelAttribute @Valid DiaryModifyRequest diaryModifyRequest,
            @RequestParam("uploadFiles") List<MultipartFile> uploadFiles,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        TravelDiary travelDiary = new TravelDiary();
        travelDiary.setTravelNo(diaryModifyRequest.getTravelNo());
        travelDiary.setDiaryTitle(diaryModifyRequest.getDiaryTitle());
        travelDiary.setDiaryContent(diaryModifyRequest.getDiaryContent());
        travelDiary.setMemberId(MemberUtils.getMemberIdFromSession(session));

        int result = travelDiaryService.modifyDiary(travelDiary, uploadFiles);
        if (result == 0) {
            redirectAttributes.addFlashAttribute("message", "수정 실패");
            return "redirect:/modify-diary";
        }
        redirectAttributes.addFlashAttribute("message", "수정 성공");
        return "redirect:/diary";
    }
    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 여행 기록 삭제
     */
    @GetMapping("/remove/{diaryNo}")
    public String removeDiary(HttpSession session, @PathVariable int diaryNo){
        int result = travelDiaryService.removeDiary(diaryNo,MemberUtils.getMemberIdFromSession(session));
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return "redirect:/diary";
    };


}
