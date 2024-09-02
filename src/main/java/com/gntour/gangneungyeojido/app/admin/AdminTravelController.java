package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showAllTravelPage(Model model, @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage) {
        model.addAttribute("page", travelService.getAllTravelsPage(currentPage));
        return "admin/travel-list";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 상세 정보 조회, [관리자 기능(페이지 폼)] 등록된 여행지 수정
     */
    @GetMapping("/admin/travel/{travelNo}")
    public String showTravelDetailPage(@PathVariable Long travelNo
            , Model model) {
        model.addAttribute("detail", travelService.getDetailTravel(travelNo));
        return "admin/travel-detail";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 수정
     */
    @PostMapping("/modify")
    public String updateTravelInfo(TravelInfo travelInfo) {
        int result = travelService.modifyTravel();
        return "redirect:/admin/travel-detail/{travelNo}";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 삭제
     */
    public void removeTravelInfo() {

    }

}
