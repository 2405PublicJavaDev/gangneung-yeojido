package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.app.admin.dto.ReqMarkAddRequest;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminMarkerController {
    private final TravelService travelService;
    /**
     * 담당자: 조승효님
     * 관련기능: [관리자 기능] 마커 승인 요청 리스트 조회
     */
    @GetMapping("/admin/marker")
    public String showReqMarkerListPage(Model model, @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage) {
        model.addAttribute("page", travelService.getRequestMarkList(currentPage));
        return "admin/marker-list";
    }

    /**
     * 담당자: 조승효님
     * 관련기능: [관리자 기능] 마커 승인 요청 상세 조회
     */
    @GetMapping("/admin/marker/{reqAddMarkerNo}")
    public String showReqMarkerDetailPage(@PathVariable Long reqAddMarkerNo, Model model) {
        model.addAttribute("detail", travelService.getRequestMarkDetail(reqAddMarkerNo));
        return "admin/marker-detail";
    }

    /**
     * 담당자: 조승효님
     * 관련기능: [관리자 기능] 마커 승인 요청 승인, 거부 정하기
     */
    @PostMapping("/admin/marker")
    @ResponseBody
    public EmptyResponse determineReqMarkerAccept(@RequestBody @Valid ReqMarkAddRequest reqMarkAdd) {
        int result = travelService.determineRequestMark(reqMarkAdd);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }
}
