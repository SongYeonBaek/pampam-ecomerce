package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessConsumerSignupRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ConsumerSignupRes result;
    private Boolean success;
}
