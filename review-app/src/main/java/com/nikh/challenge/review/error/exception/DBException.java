package com.nikh.challenge.review.error.exception;

import org.springframework.http.HttpStatus;

public class DBException extends ApiException{

    private static final String DB_CONNECTION_EXCEPTION_TEXT = "Failed to update DB";

    public DBException() {
        super(DB_CONNECTION_EXCEPTION_TEXT, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
