package com.nikh.challenge.review.controller;

import com.nikh.challenge.review.dto.ReviewBean;
import com.nikh.challenge.review.dto.ReviewRequest;
import com.nikh.challenge.review.service.AuthService;
import com.nikh.challenge.review.service.ReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review/{productId}")
public class ReviewController extends SecurityController{

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService, AuthService authService) {
        super(authService);
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<ReviewBean> getReview(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(reviewService.getReview(productId));
    }

    @PostMapping
    public ResponseEntity createReview(@PathVariable("productId") String productId,
                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                      @RequestBody ReviewRequest request) {
        if (!authorize(authorization)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to auth");
        }
        reviewService.postReview(prepareReviewBean(productId, request));
        return ResponseEntity.ok("SUCCESS");
    }

    @PutMapping
    public ResponseEntity updateReview(@PathVariable("productId") String productId,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                        @RequestBody ReviewRequest request) {
        if (!authorize(authorization)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to auth");
        }
        reviewService.updateReview(prepareReviewBean(productId, request));
        return ResponseEntity.ok("SUCCESS");
    }

    @DeleteMapping
    public ResponseEntity deleteReview(@PathVariable("productId") String productId,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        if (!authorize(authorization)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to auth");
        }
        reviewService.deleteReview(productId);
        return ResponseEntity.ok("SUCCESS");
    }

    private ReviewBean prepareReviewBean(String productId, ReviewRequest request) {
        ReviewBean response = new ReviewBean();
        response.setProductId(productId);
        response.setReviewNum(request.getReviewNum());
        response.setAvgScore(request.getAvgScore());
        return response;
    }

}
