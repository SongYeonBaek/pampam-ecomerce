package com.example.com.demo.member.exception;

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

    INTERNAL_SERVER_ERROR(7000, HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생하였습니다.")
    ;



    private final Integer code;
    private final HttpStatus status;
    private final String message;
}
