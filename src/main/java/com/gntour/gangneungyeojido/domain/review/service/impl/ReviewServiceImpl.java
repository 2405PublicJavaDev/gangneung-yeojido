package com.gntour.gangneungyeojido.domain.review.service.impl;

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
    private final ReviewMapper reviewMapper;
    private final FileUtil fileUtil;

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
        return reviewMapper.deleteReview(reviewNo);
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
