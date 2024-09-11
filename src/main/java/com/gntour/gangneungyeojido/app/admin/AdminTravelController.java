package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.app.travel.dto.TravelListSearchCondition;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminTravelController {
    private final TravelService travelService;

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 리스트 확인
     */
    @GetMapping("/admin/travel")
    public String showAllTravelPage(
            Model model,
            @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value="travelName", required = false) String travelName
    ) {
        model.addAttribute("page", travelService.getAllTravelsPage(currentPage, 10, new TravelListSearchCondition(travelName, null, null, null)));
        return "admin/travel-list";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 상세 정보 조회, [관리자 기능(페이지 폼)] 등록된 여행지 수정
     */
    @GetMapping("/admin/travel/{travelNo}")
    public String showTravelDetailPage(@PathVariable Long travelNo, Model model) {
        model.addAttribute("detail", travelService.getDetailTravel(travelNo));
        return "admin/travel-detail";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 수정
     */
    @PostMapping("/admin/travel")
    @ResponseBody
    public EmptyResponse updateTravelInfo(@RequestBody @Valid TravelInfo travelInfo) {
        log.info("modify travel info {}", travelInfo);
        int result = travelService.modifyTravel(travelInfo);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 삭제
     */
    @DeleteMapping("/admin/travel/{travelNo}")
    @ResponseBody
    public EmptyResponse removeTravelInfo(@PathVariable Long travelNo) {
        int result = travelService.removeTravel(travelNo);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }

}
