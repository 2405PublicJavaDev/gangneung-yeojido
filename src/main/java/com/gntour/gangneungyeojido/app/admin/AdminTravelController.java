package com.gntour.gangneungyeojido.app.admin;

import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class AdminTravelController {
    private TravelService tService;

    public AdminTravelController() {}

    @Autowired
    public AdminTravelController(TravelService tService) {
        this.tService = tService;
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 리스트 확인
     */
    @GetMapping("/admin/travel-list")
    public String showAllTravelPage(Model model) {
        log.info("showAllTravelPage");
        List<TravelInfo> tList = tService.getAllTravels();
        model.addAttribute("tList", tList);
        return "admin/travel-list";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 상세 정보 조회, [관리자 기능(페이지 폼)] 등록된 여행지 수정
     */
    @GetMapping("/admin/travel-detail/{travelNo}")
    public String showTravelDetailPage(@PathVariable Long travelNo
            , Model model) {
        log.info("showTravelDetailPage");
        TravelInfo travelInfo = tService.getDetailTravel(travelNo);
        return "admin/travel-detail";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 수정
     */
    @PostMapping("/modify")
    public String updateTravelInfo(TravelInfo travelInfo) {
        int result = tService.modifyTravel();
        return "redirect:/admin/travel-detail/{travelNo}";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 삭제
     */
    public void removeTravelInfo() {

    }

}
