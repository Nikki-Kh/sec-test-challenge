package com.nikh.challenge.service;

import com.nikh.challenge.dao.ReviewMapper;
import com.nikh.challenge.dto.ReviewBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewMapper reviewMapper;


    public ReviewBean getReview(String productId) {
        return reviewMapper.getReviewById(productId);
    }

    @Override
    public void updateReview(ReviewBean review) {
        int dbResponse = reviewMapper.updateReview(review);
        if (dbResponse != 1) {
            throw new RuntimeException("failed to update db");
        }
    }

    public void postReview(ReviewBean review) {
        int dbResponse = reviewMapper.insertReview(review);
        if (dbResponse != 1) {
            throw new RuntimeException("failed to update db");
        }
    }

    public void deleteReview(String productId) {
        int dbResponse = reviewMapper.deleteReviewById(productId);
        if (dbResponse != 1) {
            throw new RuntimeException("failed to update db");
        }
    }

}
