package com.gntour.gangneungyeojido.app.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminTravelController {

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 리스트 확인
     */
    @GetMapping("/admin/travel-list")
    public String showAllTravelPage() {
        log.info("showAllTravelPage");
        return "admin/travel-list";
    }


    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 상세 정보 조회, [관리자 기능(페이지 폼)] 등록된 여행지 수정
     */
    public void showTravelDetailPage() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 수정
     */
    public void updateTravelInfo() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [관리자 기능] 등록된 여행지 삭제
     */
    public void removeTravelInfo() {

    }

}
