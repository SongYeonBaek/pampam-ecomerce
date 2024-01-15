package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessConsumerLoginRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ConsumerLoginRes result;
    private Boolean success;
}
