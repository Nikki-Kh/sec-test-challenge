package com.nikh.challenge.product.dto;

import lombok.Data;

@Data
public class ReviewResponse {

    private String status;

    private String message;

    private ReviewBean review;
}
