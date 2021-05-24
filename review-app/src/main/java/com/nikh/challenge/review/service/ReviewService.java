package com.nikh.challenge.review.service;

import com.nikh.challenge.review.dto.ReviewBean;

public interface ReviewService {

    ReviewBean getReview(String productId);

    void updateReview(ReviewBean review);

    void deleteReview(String productId);

    void postReview(ReviewBean review);
}
