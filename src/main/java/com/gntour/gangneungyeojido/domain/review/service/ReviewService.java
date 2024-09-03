package com.gntour.gangneungyeojido.domain.review.service;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;

public interface ReviewService {
    void getAllReviewsByTravel();
    void getAllReviewsByMember();
    Page<ReviewComplain, Void> getAllComplainReviews(int currentPage);
    void addReview();
    void modifyReview();
    void removeReview();
    void complainReview();
    void addReviewReply();
    void modifyReviewReply();
    void removeReviewReply();
}
