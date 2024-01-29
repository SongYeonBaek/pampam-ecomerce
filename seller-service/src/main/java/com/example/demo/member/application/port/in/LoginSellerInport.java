package com.example.demo.member.application.port.in;


import com.example.demo.member.domain.JwtToken;

public interface LoginSellerInport {
    JwtToken login(LoginSellerCommand command);
}
