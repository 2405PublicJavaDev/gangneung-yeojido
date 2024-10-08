package com.gntour.gangneungyeojido.domain.review.service;

import com.gntour.gangneungyeojido.app.admin.dto.AdminComplainResponse;
import com.gntour.gangneungyeojido.app.travel.dto.ReplyModifyRequest;
import com.gntour.gangneungyeojido.app.my.dto.MyReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.ReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;

import com.gntour.gangneungyeojido.domain.review.vo.Review;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    Page<ReviewResponse, TravelSearchCondition> getAllReviewsByTravel(Integer currentPage, Long travelNo, Long reviewNo, String order);
    Page<AdminComplainResponse, Void> getAllComplainReviews(int currentPage);
    Page<MyReviewResponse, String> getAllReviewsByMember(Integer currentPage, String memberId);
    int addReview(List<MultipartFile> uploadFiles, Review review);
    int modifyReview(Review review, List<MultipartFile> reloadFile);
    int removeReview(Long reviewNo, String memberId);
    int complainReview(ReviewComplain reviewComplain);
    int addReviewReply(Review review);
    int modifyReviewReply(ReplyModifyRequest replyModifyRequest);
    void removeReviewReply();

    ReviewResponse getMyReview(Long travelNo, String memberId);
    ReviewResponse getMyReply(Long reviewNo, String memberId);
}
