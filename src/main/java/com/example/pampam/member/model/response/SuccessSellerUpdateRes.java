package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessSellerUpdateRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private SellerUpdateRes result;
    private Boolean success;
}
