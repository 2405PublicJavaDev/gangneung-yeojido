package com.gntour.gangneungyeojido.domain.review.service;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;

import com.gntour.gangneungyeojido.domain.review.vo.Review;

import java.util.List;

public interface ReviewService {
    Page<Review, Long> getAllReviewsByTravel(Integer currentPage, Long travelNo);
    Page<ReviewComplain, Void> getAllComplainReviews(int currentPage);
    List<Review> getAllReviewsByMember(String memberId);
    int addReview(Review review);
    void modifyReview();
    void removeReview();
    void complainReview();
    void addReviewReply();
    void modifyReviewReply();
    void removeReviewReply();
}
