package com.gntour.gangneungyeojido.app.travel;

import com.gntour.gangneungyeojido.domain.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TravelController {
    private final TravelService tService;

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리스트 조회
     */
    public String showTravelListPage() {
        return "";
    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 상세 정보 조회, [여행지 기능] 여행지 리뷰 리스트 조회
     */
    public void showTravelDetailPage() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리스트 조회
     */
    public void getTravelList() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 등록
     */
    public void addReview() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 수정
     */
    public void updateReview() {

    }

    /**
     * 담당자 : 엄태운님
     * 관련기능 : [여행지 기능] 여행지 리뷰 삭제
     */
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
