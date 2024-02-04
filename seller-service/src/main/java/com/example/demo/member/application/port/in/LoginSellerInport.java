package com.example.demo.member.application.port.in;


import com.example.demo.member.common.BaseResponse;

public interface LoginSellerInport {
    BaseResponse<Object> login(LoginSellerCommand command);
}
