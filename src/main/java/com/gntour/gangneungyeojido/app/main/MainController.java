package com.gntour.gangneungyeojido.app.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gntour.gangneungyeojido.app.admin.dto.ReqMarkAddRequest;
import com.gntour.gangneungyeojido.app.main.dto.ReqMarkAddInMainRequest;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final TravelService travelService;
    /**
     * 담당자: 조승효님
     * 관련기능: [메인 기능] 여행지 마커 리스트 조회, [메인 기능] 이달의 신규 여행지 확인
     */
    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("travelInfos", travelService.getAllTravels());
        model.addAttribute("thisMonth", travelService.getThisMonthTravel());
        return "main/index";
    }

    /**
     * 담당자: 조승효님
     * 관련기능: [메인 기능(페이지 폼)] 마커 승인 요청
     */
    @GetMapping("/mark-request")
    public String showAddReqMarkerPage() {
        return "main/mark-request";
    }

    /**
     * 담당자: 조승효님
     * 관련기능: [메인 기능] 여행지 마커에 대한 개요 확인
     */
    @GetMapping("/travel/{travelNo}")
    @ResponseBody
    public TravelInfo getMarkerOutline(@PathVariable Long travelNo) {
        return travelService.getDetailTravel(travelNo);
    }

    /**
     * 담당자: 조승효님
     * 관련기능: [메인 기능] 마커 승인 요청
     */
    @PostMapping("/mark-request")
    @ResponseBody
    public EmptyResponse addReqMarker(HttpSession session, @RequestBody @Valid ReqMarkAddInMainRequest req) {
        req.setMemberId(MemberUtils.getMemberIdFromSession(session));
        int result = travelService.addRequestMark(req);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }
}
