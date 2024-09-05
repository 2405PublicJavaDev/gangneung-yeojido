package com.gntour.gangneungyeojido.domain.review.service.impl;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.mapper.ReviewMapper;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import lombok.RequiredArgsConstructor;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper rMapper;

    @Override
    public Page<Review, Long> getAllReviewsByTravel(Integer currentPage, Long travelNo) {
        return Page.of(currentPage,rMapper.selectAllReviewsCount(travelNo), travelNo, rMapper::selectAllReviews);
    }

    @Override
    public List<Review> getAllReviewsByMember(String memberId) {
        return rMapper.selectAllReviewsByMember(memberId);
    }

    @Override
    public Page<ReviewComplain, Void> getAllComplainReviews(int currentPage) {
        return Page.of(currentPage, rMapper.selectAllComplainReviewsCount(), rMapper::selectAllComplainReviews);

    }

    @Override
    public int addReview(Review review) {
        return rMapper.insertReview(review);
    }

    @Override
    public void modifyReview() {

    }

    @Override
    public void removeReview() {

    }

    @Override
    public void complainReview() {

    }

    @Override
    public void addReviewReply() {

    }

    @Override
    public void modifyReviewReply() {

    }

    @Override
    public void removeReviewReply() {

    }
}
