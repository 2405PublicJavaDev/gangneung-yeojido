package com.gntour.gangneungyeojido.domain.review.mapper;

public interface ReviewMapper {
    void selectAllReviews();
    void selectAllReviewsCount();
    void selectAllReviewsCountByMember();
    void selectAllReviewsFileByReviewNo();
    void selectAllComplainReviews();
    void selectAllComplainReviewsCount();
    void insertReview();
    void insertReviewFile();
    void insertReviewComplain();
    void updateReview();
    void deleteReview();
    void deleteReviewFile();
}
