package com.example.pampam.member.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class SellerLoginReq {
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    private String password;
}
