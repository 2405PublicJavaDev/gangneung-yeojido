package com.gntour.gangneungyeojido.domain.review.mapper;

public interface ReviewMapper {

    public void selectAllReviews();
    public void selectAllReviewsCountByMember();
    public void selectAllComplainReviews();
    public void insertReview();
    public void insertReviewComplain();
    public void updateReview();
    public void deleteReview();
}
