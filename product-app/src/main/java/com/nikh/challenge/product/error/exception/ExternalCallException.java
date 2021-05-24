package com.nikh.challenge.product.error.exception;

import org.springframework.http.HttpStatus;

public class ExternalCallException  extends ApiException{

    private static final String EXTERNAL_CALL_EXCEPTION_TEXT = "Failed to retrieve info for product = ";

    public ExternalCallException(String productId) {
        super(EXTERNAL_CALL_EXCEPTION_TEXT + productId, HttpStatus.FAILED_DEPENDENCY);
    }
}
