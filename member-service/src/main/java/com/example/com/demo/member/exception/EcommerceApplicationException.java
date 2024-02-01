package com.example.com.demo.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EcommerceApplicationException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;
    private Integer code;

    public EcommerceApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
        this.code = null;
    }

    public EcommerceApplicationException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }


    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
