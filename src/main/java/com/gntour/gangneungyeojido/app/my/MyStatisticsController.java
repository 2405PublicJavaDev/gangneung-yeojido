package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.domain.mytravel.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyStatisticsController {
    private StatisticsService statisticsService;

    /**
     * 담당자 : 백인호님
     * 관련 기능 :[마이페이지 기능] 즐겨찾기, 여행 기록, 문의 내역, 답변 내역(문의 내역에 관리자가 답변한 것) 등록 건수 조회
     */
    public void getMyStatistics() {};
}
