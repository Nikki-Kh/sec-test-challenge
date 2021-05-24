package com.nikh.challenge.review.dto;

import lombok.Data;

@Data
public class ReviewResponse {

    private String status;

    private String message;

    private ReviewBean review;
}
