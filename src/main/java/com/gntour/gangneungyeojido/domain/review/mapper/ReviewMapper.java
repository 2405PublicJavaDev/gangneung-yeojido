package com.gntour.gangneungyeojido.domain.review.mapper;

import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void selectAllReviews();
    void selectAllReviewsCount();
    void selectAllReviewsCountByMember();
    void selectAllReviewsFileByReviewNo();
    List<ReviewComplain> selectAllComplainReviews(Integer currentPage, RowBounds rowBounds);
    int selectAllComplainReviewsCount();
    List<Review> selectAllReviewsByMember(String memberId);
    void insertReview();
    void insertReviewFile();
    void insertReviewComplain();
    void updateReview();
    void deleteReview();
    void deleteReviewFile();
}
