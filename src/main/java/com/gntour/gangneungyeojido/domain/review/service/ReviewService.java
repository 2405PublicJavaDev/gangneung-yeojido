package com.gntour.gangneungyeojido.domain.review.service;

public interface ReviewService {

    public void getAllReviewsByTravel();
    public void getAllReviewsByMember();
    public void getAllComplainReviews();
    public void addReview();
    public void updateReview();
    public void removeReview();
    public void complainReview();
    public void addReviewReply();
    public void updateReviewReply();
    public void removeReviewReply();
}
