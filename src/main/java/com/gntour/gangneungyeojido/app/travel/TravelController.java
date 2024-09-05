package com.gntour.gangneungyeojido.app.travel;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;
    private final ReviewService reviewService;

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리스트 조회
     */
    @GetMapping("/travel/list")
    public String showTravelListPage(Model model
            , @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage) {
        model.addAttribute("page", travelService.getAllTravelsPage(currentPage, 12));
        return "travel/travel-list";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 상세 정보 조회, [여행지 기능] 여행지 리뷰 리스트 조회, [여행지 기능] 여행지 별점 조회
     */
    @GetMapping("/travel/detail/{travelNo}")
    public String showTravelDetailPage(@PathVariable Long travelNo, Model model) {
        model.addAttribute("detail", travelService.getDetailTravel(travelNo));
        model.addAttribute("score", travelService.getScoreByTravelNo(travelNo));
        return "travel/travel-detail";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 리스트 조회
     */
    @GetMapping("/travel/review/{travelNo}")
    @ResponseBody
    public Page<Review, Long> showTravelReviewPage(@PathVariable Long travelNo
            , @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage) {
        return reviewService.getAllReviewsByTravel(currentPage, travelNo);
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 등록
     */
    @PostMapping("/review/add")
    @ResponseBody
    public EmptyResponse addReview(Review review, HttpSession session) {
        String reviewWriter = (String)session.getAttribute("memberId");
        review.setMemberId(reviewWriter);
        int result = reviewService.addReview(review);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 수정
     */
    @PostMapping("/review/modify")
    @ResponseBody
    public void updateReview() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 삭제
     */
    @PostMapping("/review/remove")
    @ResponseBody
    public void removeReview() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 신고
     */
    public void complainReview() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 댓글 등록
     */
    public void addReviewReply() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 댓글 수정
     */
    public void updateReviewReply() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 댓글 삭제
     */
    public void removeReviewReply() {

    }

}
