package com.gntour.gangneungyeojido.domain.review.service;

import com.gntour.gangneungyeojido.app.travel.dto.ReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelSearchCondition;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;

import com.gntour.gangneungyeojido.domain.review.vo.Review;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    Page<ReviewResponse, TravelSearchCondition> getAllReviewsByTravel(Integer currentPage, Long travelNo, Long reviewNo);
    Page<ReviewComplain, Void> getAllComplainReviews(int currentPage);
    List<Review> getAllReviewsByMember(String memberId);
    int addReview(List<MultipartFile> uploadFiles, Review review);
    int modifyReview(Review review, List<MultipartFile> reloadFile);
    int removeReview(Long reviewNo);
    void complainReview(String category);
    int addReviewReply(Review review);
    void modifyReviewReply();
    void removeReviewReply();

    ReviewResponse getMyReview(Long travelNo, String memberId);
}
