package com.nikh.challenge.service;

import com.nikh.challenge.dto.ReviewBean;

public interface ReviewService {

    ReviewBean getReview(String productId);

    void updateReview(ReviewBean review);

    void deleteReview(String productId);

    void postReview(ReviewBean review);
}
