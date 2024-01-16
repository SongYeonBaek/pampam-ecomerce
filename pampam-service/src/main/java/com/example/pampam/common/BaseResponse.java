package com.example.pampam.common;

import com.example.pampam.member.model.response.ConsumerLoginRes;
import lombok.*;
import org.springframework.http.HttpEntity;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private T result;
    private Boolean success;

    public static <T> BaseResponse<T> successResponse(String message, T result) {
        return new BaseResponse<>(true, 1000, message, result, true);
    }

    public static <T> BaseResponse<T> failResponse(Integer code, String message) {
        return new BaseResponse<>(false, code, message, null, false);
    }
}
