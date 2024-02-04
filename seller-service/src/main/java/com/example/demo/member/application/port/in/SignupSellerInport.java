package com.example.demo.member.application.port.in;


import com.example.demo.member.common.BaseResponse;

public interface SignupSellerInport {
    BaseResponse<Object> signupSeller(SignupSellerCommand command);
}
