package com.example.com.demo.member.application.port.in;

import com.example.com.demo.member.common.BaseResponse;
import com.example.com.demo.member.domain.JwtToken;

public interface LoginMemberInport {
    BaseResponse<String> login(LoginMemberCommand command);
}
