package com.gntour.gangneungyeojido.app.my;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
@Slf4j
public class MyReviewController {

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 리뷰 리스트 조회
     */
    @GetMapping("/review")
    public String showMyReviewPage() {
        return "/myPage/myReview";
    };
}
