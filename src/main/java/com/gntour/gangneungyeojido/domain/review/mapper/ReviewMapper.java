package com.gntour.gangneungyeojido.domain.review.mapper;

import com.gntour.gangneungyeojido.app.my.dto.MyReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.ReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelSearchCondition;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<ReviewResponse> selectAllReviews(Integer currentPage, TravelSearchCondition condition, RowBounds rowBounds);
    int selectAllReviewsCount(TravelSearchCondition condition);
    int selectAllReviewsCountByMember(String memberId);
    List<ReviewFile> selectAllReviewsFileByReviewNo(Long reviewNo);
    List<ReviewComplain> selectAllComplainReviews(Integer currentPage, RowBounds rowBounds);
    int selectAllComplainReviewsCount();
    List<MyReviewResponse> selectAllReviewsByMember(Integer currentPage, String memberId, RowBounds rowBounds);
    int insertReview(Review review);
    int insertReviewFile(ReviewFile reviewFile);
    void insertReviewComplain(String category);
    int updateReview(Review review);
    int deleteReview(Long reviewNo);
    int deleteReviewFile(Long reviewNo);

    ReviewResponse selectMyReview(Long travelNo, String memberId);
}
