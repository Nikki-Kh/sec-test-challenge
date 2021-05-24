package com.nikh.challenge.dto;

import lombok.Data;

@Data
public class ReviewResponse {

    private String status;

    private String message;

    private ReviewBean review;
}
