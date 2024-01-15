package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessSellerLoginRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private SellerLoginRes result;
    private Boolean success;
}
