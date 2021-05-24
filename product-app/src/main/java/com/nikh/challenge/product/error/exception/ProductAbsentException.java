package com.nikh.challenge.product.error.exception;

import org.springframework.http.HttpStatus;

public class ProductAbsentException extends ApiException{


    private static final String PRODUCT_ABSENT_EXCEPTION_TEXT = "Product info not found: product = ";

    public ProductAbsentException(String productId) {
        super(PRODUCT_ABSENT_EXCEPTION_TEXT + productId, HttpStatus.NOT_FOUND);
    }

}
