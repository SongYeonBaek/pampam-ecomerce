package com.example.com.demo.member.application.port.in;

import com.example.com.demo.member.domain.JwtToken;

public interface LoginMemberInport {
    JwtToken login(LoginMemberCommand command);
}
