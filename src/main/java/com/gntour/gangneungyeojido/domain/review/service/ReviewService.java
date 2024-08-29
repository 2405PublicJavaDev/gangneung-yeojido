package com.gntour.gangneungyeojido.domain.review.service;

public interface ReviewService {
    void getAllReviewsByTravel();
    void getAllReviewsByMember();
    void getAllComplainReviews();
    void addReview();
    void modifyReview();
    void removeReview();
    void complainReview();
    void addReviewReply();
    void modifyReviewReply();
    void removeReviewReply();
}
