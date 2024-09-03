package com.gntour.gangneungyeojido.domain.review.service.impl;

import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.domain.review.mapper.ReviewMapper;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper rMapper;

    @Override
    public void getAllReviewsByTravel() {

    }

    @Override
    public void getAllReviewsByMember() {

    }

    @Override
    public Page<ReviewComplain, Void> getAllComplainReviews(int currentPage) {
        return Page.of(currentPage, rMapper.selectAllComplainReviewsCount(), rMapper::selectAllComplainReviews);

    }

    @Override
    public void addReview() {

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
