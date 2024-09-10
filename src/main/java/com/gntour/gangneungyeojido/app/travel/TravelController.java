package com.gntour.gangneungyeojido.app.travel;

import com.gntour.gangneungyeojido.app.travel.dto.ReplyAddRequest;
import com.gntour.gangneungyeojido.app.travel.dto.ReplyModifyRequest;
import com.gntour.gangneungyeojido.app.travel.dto.ReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelListResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelListSearchCondition;
import com.gntour.gangneungyeojido.app.travel.dto.TravelSearchCondition;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.EmptyResponse;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.mytravel.service.FavoritesService;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import com.gntour.gangneungyeojido.domain.travel.vo.TravelInfo;
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
    private final FavoritesService favoritesService;

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리스트 조회
     */
    @GetMapping("/travel/list")
    public String showTravelListPage(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(value = "travelName", required = false) String travelName,
            @RequestParam(value = "region", required = false) List<String> region,
            @RequestParam(value = "category", required = false) List<String> category,
            Model model
    ) {
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("travelName", travelName);
        model.addAttribute("region", region);
        model.addAttribute("category", category);
        return "travel/travel-list";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리스트 조회, [여행지 기능] 검색한 여행지 리스트 조회
     */
    @GetMapping("/travel")
    @ResponseBody
    public Page<TravelListResponse, TravelListSearchCondition> getTravelList(
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
             @RequestParam(value = "travelName", required = false) String travelName,
            @RequestParam(value = "region", required = false) List<String> region,
            @RequestParam(value = "category", required = false) List<String> category,
            HttpSession session
            ) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        return travelService.getAllTravelsPage(currentPage, 12,new TravelListSearchCondition(travelName, region, category, memberId));
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 상세 정보 조회
     */
    @GetMapping("/travel/detail/{travelNo}")
    public String showTravelDetailPage(@PathVariable Long travelNo, Model model, HttpSession session) {
        model.addAttribute("detail", travelService.getDetailTravel(travelNo));
        model.addAttribute("favoritesNo", favoritesService.getFavoritesNo(MemberUtils.getMemberIdFromSession(session), travelNo));
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
        if(memberId != null) {
            ReviewResponse reviewResponse = reviewService.getMyReview(travelNo, memberId);
            if(reviewResponse != null) {
                return reviewResponse;
            }
        }
        return new ReviewResponse();
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
           , @ModelAttribute @Valid Review review
           , @RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFiles) {
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
     * 관련기능 : [여행지 기능] 여행지 리뷰 수정 팝업창
     */
    @GetMapping("/review/modify/{travelNo}")
    @ResponseBody
    public ReviewResponse showModifyReviewPopup(@PathVariable Long travelNo, HttpSession session) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        log.info(memberId);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        ReviewResponse reviewResponse = reviewService.getMyReview(travelNo, memberId);
        if(reviewResponse != null) {
            return reviewResponse;
        }
        return new ReviewResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 수정
     */
    @PutMapping("/review/modify")
    @ResponseBody
    public EmptyResponse updateReview(Review review
            , @RequestParam(value = "reloadFile", required = false) List<MultipartFile> reloadFile, HttpSession session) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        review.setMemberId(memberId);
        int result = reviewService.modifyReview(review, reloadFile);
        return new EmptyResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 삭제
     */
    @DeleteMapping("/review/remove/{reviewNo}")
    @ResponseBody
    public EmptyResponse removeReview(@PathVariable Long reviewNo) {
        int result = reviewService.removeReview(reviewNo);
        return new EmptyResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 신고 페이지
     */
    @GetMapping("/review/complain/{travelNo}")
    public String showComplainPage(@PathVariable Long travelNo, Model model, @RequestParam Long reviewNo) {
        model.addAttribute("travelNo", travelNo);
        model.addAttribute("reviewNo", reviewNo);
        return "travel/travel-complain";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 신고
     */
    @PostMapping("/review/complain/{travelNo}")
    public String complainReview(HttpSession session,
           @RequestParam String complainKeyword,
           @RequestParam Long reviewNo,
           @PathVariable Long travelNo) {
        ReviewComplain complain = new ReviewComplain();
        complain.setCategory(complainKeyword);
        complain.setReviewNo(reviewNo);
        String memberId = MemberUtils.getMemberIdFromSession(session);
        complain.setMemberId(memberId);
        int result = reviewService.complainReview(complain);
        return "redirect:/travel/detail/{travelNo}";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 댓글 등록
     */
    @PostMapping("/reply/add")
    @ResponseBody
    public EmptyResponse addReviewReply(HttpSession session,
            @RequestBody ReplyAddRequest replyAddRequest) {
        String replyWriter = MemberUtils.getMemberIdFromSession(session);
        if(replyWriter == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        log.info(replyAddRequest.toString());
        Review review = new Review();
        review.setScore(5.0);
        review.setMemberId(replyWriter);
        review.setReviewContent(replyAddRequest.getReviewContent());
        review.setParentReviewNo(replyAddRequest.getParentReviewNo());
        review.setTravelNo(replyAddRequest.getTravelNo());
        log.info(review.toString());
        int result = reviewService.addReviewReply(review);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 댓글 수정 팝업창
     */
    @GetMapping("/reply/modify/{reviewNo}")
    @ResponseBody
    public ReviewResponse showModifyReplyPopup(@PathVariable Long reviewNo
            , HttpSession session) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        ReviewResponse reviewResponse = reviewService.getMyReply(reviewNo, memberId);
        if(reviewResponse != null) {
            return reviewResponse;
        }
        return new ReviewResponse();
    }
    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 댓글 수정
     */
    @PutMapping("/reply/modify")
    @ResponseBody
    public EmptyResponse updateReviewReply(HttpSession session
            , @RequestBody ReplyModifyRequest replyModifyRequest) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        log.info(replyModifyRequest.toString());
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        int result = reviewService.modifyReviewReply(replyModifyRequest);
        if(result == 0) {
            throw new BusinessException(ErrorCode.NO_UPDATE);
        }
        return new EmptyResponse();
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 댓글 삭제
     * - 리뷰 삭제 로직과 동일하게 사용하므로 작성하지 않음 -
     */
//    public void removeReviewReply() {
//
//    }

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
