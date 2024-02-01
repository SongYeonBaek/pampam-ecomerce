package com.example.com.demo.member.exception;

import com.example.com.demo.member.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = EcommerceApplicationException.class)
    public ResponseEntity<Object> applicationHandler(EcommerceApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(BaseResponse.failResponse(e.getErrorCode().getCode(), e.getErrorCode().name()));
    }

}
