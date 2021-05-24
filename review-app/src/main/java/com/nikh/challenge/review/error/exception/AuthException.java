package com.nikh.challenge.review.error.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends  ApiException{

    private static final String AUTH_EXCEPTION_TEXT = "Failed to authorize";

    public AuthException() {
        super(AUTH_EXCEPTION_TEXT, HttpStatus.UNAUTHORIZED);
    }
}
