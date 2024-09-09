package com.gntour.gangneungyeojido.domain.review.service.impl;

import com.gntour.gangneungyeojido.app.travel.dto.ReviewResponse;
import com.gntour.gangneungyeojido.app.travel.dto.TravelSearchCondition;
import com.gntour.gangneungyeojido.common.FileUtil;
import com.gntour.gangneungyeojido.common.Page;
import com.gntour.gangneungyeojido.common.UploadCategory;
import com.gntour.gangneungyeojido.domain.review.mapper.ReviewMapper;
import com.gntour.gangneungyeojido.domain.review.service.ReviewService;
import com.gntour.gangneungyeojido.domain.review.vo.Review;
import lombok.RequiredArgsConstructor;
import com.gntour.gangneungyeojido.domain.review.vo.ReviewComplain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper rMapper;
    private final FileUtil fileUtil;

    @Override
    public Page<ReviewResponse, TravelSearchCondition> getAllReviewsByTravel(Integer currentPage, Long travelNo, Long reviewNo) {
        return Page.of(currentPage,rMapper.selectAllReviewsCount(new TravelSearchCondition(travelNo, reviewNo)), new TravelSearchCondition(travelNo, reviewNo), rMapper::selectAllReviews);
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
    public int addReview(List<MultipartFile> uploadFile, Review review) {
        int result = rMapper.insertReview(review);
        try {
            result += fileUtil.uploadFiles(UploadCategory.REVIEW, uploadFile, review.getReviewNo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int modifyReview(Review review) {
        return rMapper.updateReview(review);
    }

    @Override
    public int removeReview(Long reviewNo) {
        return rMapper.deleteReview(reviewNo);
    }

    @Override
    public void complainReview(String category) {
//        return rMapper.
    }

    @Override
    public int addReviewReply(Review review) { return rMapper.insertReview(review); }

    @Override
    public void modifyReviewReply() {

    }

    @Override
    public void removeReviewReply() {

    }

    @Override
    public ReviewResponse getMyReview(Long travelNo, String memberId) {
        return rMapper.selectMyReview(travelNo, memberId);
    }
}
