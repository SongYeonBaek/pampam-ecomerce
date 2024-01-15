package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class SuccessConsumerUpdateRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private ConsumerUpdateRes result;
    private Boolean success;
}
