package com.gntour.gangneungyeojido.domain.review.mapper;

import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<Review> selectAllReviews(Integer currentPage, Long travelNo, RowBounds rowBounds);
    int selectAllReviewsCount(Long travelNo);
    void selectAllReviewsCountByMember();
    List<ReviewFile> selectAllReviewsFileByReviewNo(Long reviewNo);
    List<ReviewComplain> selectAllComplainReviews(Integer currentPage, RowBounds rowBounds);
    int selectAllComplainReviewsCount();
    List<Review> selectAllReviewsByMember(String memberId);
    int insertReview(Review review);
    int insertReviewFile(ReviewFile reviewFile);
    void insertReviewComplain();
    int updateReview(Review review);
    int deleteReview(Long reviewNo);
    int deleteReviewFile(Long reviewNo);
}
