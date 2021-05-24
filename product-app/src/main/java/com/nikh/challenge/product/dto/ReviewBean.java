package com.nikh.challenge.product.dto;

import lombok.Data;

@Data
public class ReviewBean {

    private String productId;

    private Float avgScore;

    private Integer reviewNum;

}
