package com.gntour.gangneungyeojido.domain.review.service.impl;

import com.gntour.gangneungyeojido.app.travel.dto.ReplyModifyRequest;
import com.gntour.gangneungyeojido.app.my.dto.MyReviewResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper rMapper;
    private final FileUtil fileUtil;

    @Override
    public Page<ReviewResponse, TravelSearchCondition> getAllReviewsByTravel(Integer currentPage, Long travelNo, Long reviewNo) {
        return Page.of(currentPage,rMapper.selectAllReviewsCount(new TravelSearchCondition(travelNo, reviewNo)), new TravelSearchCondition(travelNo, reviewNo), rMapper::selectAllReviews);
    }

    @Override
    public Page<MyReviewResponse, String> getAllReviewsByMember(Integer currentPage, String memberId) {
        return Page.of(currentPage, rMapper.selectAllReviewsCountByMember(memberId), memberId, rMapper::selectAllReviewsByMember, 12, 10);
    }

    @Override
    public Page<ReviewComplain, Void> getAllComplainReviews(int currentPage) {
        return Page.of(currentPage, rMapper.selectAllComplainReviewsCount(), rMapper::selectAllComplainReviews);

    }

    @Override
    public int addReview(List<MultipartFile> uploadFile, Review review) {
        int result = rMapper.insertReview(review);
        try {
            if(uploadFile != null && uploadFile.size() > 0) {
                result += fileUtil.uploadFiles(UploadCategory.REVIEW, uploadFile, review.getReviewNo());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int modifyReview(Review review, List<MultipartFile> reloadFile) {
        log.info(review.toString());
        int result = rMapper.updateReview(review);
        try {
            fileUtil.deleteFiles(UploadCategory.REVIEW, review.getReviewNo());
            if(reloadFile != null && reloadFile.size() > 0) {
                result += fileUtil.uploadFiles(UploadCategory.REVIEW, reloadFile, review.getReviewNo());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int removeReview(Long reviewNo, String memberId) {
        return rMapper.deleteReview(reviewNo, memberId);
    }

    @Override
    public int complainReview(ReviewComplain complain) {
        return rMapper.insertReviewComplain(complain);
    }

    @Override
    public int addReviewReply(Review review) { return rMapper.insertReview(review); }

    @Override
    public int modifyReviewReply(ReplyModifyRequest replyModifyRequest) {
        return rMapper.updateReply(replyModifyRequest);
    }

    @Override
    public void removeReviewReply() {

    }

    @Override
    public ReviewResponse getMyReview(Long travelNo, String memberId) {
        return rMapper.selectMyReview(travelNo, memberId);
    }

    @Override
    public ReviewResponse getMyReply(Long reviewNo, String memberId) {
        return rMapper.selectMyReply(reviewNo, memberId);
    }
}
