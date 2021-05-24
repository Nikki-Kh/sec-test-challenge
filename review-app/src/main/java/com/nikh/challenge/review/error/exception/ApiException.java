package com.nikh.challenge.review.error.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiException extends  RuntimeException {

    String message;
    HttpStatus status;
}
