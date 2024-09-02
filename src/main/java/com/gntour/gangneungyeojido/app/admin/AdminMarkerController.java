package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public void showReqMarkerDetailPage() {

    }

    /**
     * 담당자: 조승효님
     * 관련기능: [관리자 기능] 마커 승인 요청 승인, 거부 정하기
     */
    public void determineReqMarkerAccept() {

    }
}
