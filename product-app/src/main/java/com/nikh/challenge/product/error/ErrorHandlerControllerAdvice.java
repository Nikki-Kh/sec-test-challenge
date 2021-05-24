package com.nikh.challenge.product.error;

import com.nikh.challenge.product.error.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorHandlerControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ApiException.class)
    ResponseEntity<ApiException> handleApiException(ApiException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }


}
