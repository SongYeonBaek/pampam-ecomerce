package com.example.pampam.exception;

import com.example.pampam.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(EcommerceApplicationException.class)
    public ResponseEntity<Object> applicationHandler(EcommerceApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(BaseResponse.failResponse(e.getErrorCode().getCode(), e.getErrorCode().name()));
    }
}
