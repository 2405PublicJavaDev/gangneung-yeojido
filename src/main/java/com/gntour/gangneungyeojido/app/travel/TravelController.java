package com.gntour.gangneungyeojido.app.travel;

import com.gntour.gangneungyeojido.app.travel.dto.ReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelSearchCondition;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;
    private final ReviewService reviewService;
    private final NoticeService noticeService;

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
     * 관련기능 : [여행지 기능] 검색한 여행지 리스트 조회
     */
    @PostMapping("/travel/search-list")
    public String showSearchedTravelListPage(Model model
        , @RequestParam("searchKeyword") String searchKeyword
        , @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage) {
        model.addAttribute("tList", travelService.selectSearchedTravelsPage(searchKeyword));
        System.out.print(searchKeyword);
        return "travel/search-travel-list";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 상세 정보 조회
     */
    @GetMapping("/travel/detail/{travelNo}")
    public String showTravelDetailPage(@PathVariable Long travelNo, Model model) {
        model.addAttribute("detail", travelService.getDetailTravel(travelNo));
        model.addAttribute("score", travelService.getScoreByTravelNo(travelNo));

        return "travel/travel-detail";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 내가 쓴 여행지 리뷰 리스트 조회
     * 리뷰 중에서 내가 쓴 리뷰를 보여준다.
     */
    @GetMapping("/travel/myreview/{travelNo}")
    @ResponseBody
    public ReviewResponse getMyReview(@PathVariable Long travelNo, HttpSession session) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        ReviewResponse reviewResponse = new ReviewResponse();
        if(memberId != null) {
            reviewResponse = reviewService.getMyReview(travelNo, memberId);
        }
        return reviewResponse;
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 리스트 조회
     * 여행지 리뷰를 페이지로 보여준다.
     */
    @GetMapping("/travel/review")
    @ResponseBody
    public Page<ReviewResponse, TravelSearchCondition> showTravelReviewPage(
            @RequestParam("travelNo") Long travelNo,
            @RequestParam(value = "reviewNo", required = false) Long reviewNo,
            @RequestParam(value="currentPage", defaultValue = "1") Integer currentPage) {
        Page<ReviewResponse, TravelSearchCondition> page = reviewService.getAllReviewsByTravel(currentPage, travelNo, reviewNo);
        log.info(page.getData().toString());
        return page;
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 등록
     */
    @PostMapping("/review/add")
    @ResponseBody
    public EmptyResponse addReview(HttpSession session
           , Model model
           , @ModelAttribute @Valid Review review
           , @RequestParam("uploadFile") List<MultipartFile> uploadFiles) {
        String reviewWriter = MemberUtils.getMemberIdFromSession(session);
        review.setMemberId(reviewWriter);
        int result = reviewService.addReview(uploadFiles, review);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 수정
     */
    @PostMapping("/review/modify/{reviewNo}")
    @ResponseBody
    public void updateReview(Review review, @PathVariable Long reviewNo
            , @RequestParam MultipartFile reloadFile) {
        int result = reviewService.modifyReview(review);
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 삭제
     */
    @DeleteMapping("/review/remove/{reviewNo}")
    @ResponseBody
    public String removeReview(@PathVariable Long reviewNo) {
        int result = reviewService.removeReview(reviewNo);
        return "redirect:/travel/detail/{travelNo}";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 신고
     */
    @PostMapping("/review/complain/{category}")
    @ResponseBody
    public void complainReview(@PathVariable("category") String category, HttpSession session) {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 댓글 등록
     */
    @PostMapping("/reply/add")
    @ResponseBody
    public EmptyResponse addReviewReply(HttpSession session,
            Model model,
            @ModelAttribute @Valid Review review) {
        String replyWriter = MemberUtils.getMemberIdFromSession(session);
        review.setMemberId(replyWriter);
        int result = reviewService.addReviewReply(review);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
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

    /**
     * 담당자 : 김윤경님
     * 관련 기능 : [푸터 기능] 주요 공지사항 리스트 조회
     */
    @ModelAttribute
    public String getImportantNoticeList(Model model){
        List<Notice> importantNotices = noticeService.getImportantNotices();
        model.addAttribute("footerImportantNotices", importantNotices);
        return "fragments/footer";
    }

}
