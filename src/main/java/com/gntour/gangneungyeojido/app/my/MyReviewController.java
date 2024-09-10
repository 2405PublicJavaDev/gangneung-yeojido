package com.gntour.gangneungyeojido.app.my;

import com.gntour.gangneungyeojido.app.my.dto.MyReviewResponse;
import com.gntour.gangneungyeojido.common.MemberUtils;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.exception.BusinessException;
import com.gntour.gangneungyeojido.common.exception.ErrorCode;
import com.gntour.gangneungyeojido.domain.notice.service.NoticeService;
import com.gntour.gangneungyeojido.domain.notice.vo.Notice;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyReviewController {

    private final ReviewService reviewService;
    private final NoticeService noticeService;

    /**
     * 담당자 : 백인호님
     * 관련 기능 : [마이페이지 기능] 나의 리뷰 리스트 조회
     */
    @GetMapping("/review")
    public String showMyReviewPage(HttpSession session, Model model, @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage) {
        String memberId = MemberUtils.getMemberIdFromSession(session);
        if(memberId == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAIL);
        }
        Page<MyReviewResponse, String> reviewList = reviewService.getAllReviewsByMember(currentPage, memberId);
        // log.info("reviewList {}", reviewList);
        model.addAttribute("page", reviewList);
        return "/myPage/myReview";
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
