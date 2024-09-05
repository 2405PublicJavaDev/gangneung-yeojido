package com.gntour.gangneungyeojido.domain.review.mapper;

import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void selectAllReviews();
    void selectAllReviewsCount();
    void selectAllReviewsCountByMember();
    List<ReviewFile> selectAllReviewsFileByReviewNo(Long reviewNo);
    List<ReviewComplain> selectAllComplainReviews(Integer currentPage, RowBounds rowBounds);
    int selectAllComplainReviewsCount();
    List<Review> selectAllReviewsByMember(String memberId);
    void insertReview();
    int insertReviewFile(ReviewFile reviewFile);
    void insertReviewComplain();
    void updateReview();
    void deleteReview();
    int deleteReviewFile(Long reviewNo);
}
