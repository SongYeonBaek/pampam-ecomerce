package com.example.pampam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATE_USER(3000, HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    USER_NOT_FOUND(3000, HttpStatus.NOT_FOUND, "존재하지 않은 회원입니다."),
    INVALID_TOKEN(3000, HttpStatus.UNAUTHORIZED, "인증되지 않은 토큰입니다."),
    INVALID_PASSWORD(3000, HttpStatus.UNAUTHORIZED, "비밀번호가 틀립니다."),



    DUPLICATE_PRODUCT(5000, HttpStatus.CONFLICT, "이미 존재하는 상품입니다."),
    PRODUCT_NOT_FOUND(5000, HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),


    NOT_MATCH_AMOUNT(6000, HttpStatus.BAD_REQUEST, "입금하신 금액이 상품의 가격과 다릅니다."),


    INTERNAL_SERVER_ERROR(7000, HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생하였습니다.")
    ;



    private final Integer code;
    private final HttpStatus status;
    private final String message;
}
