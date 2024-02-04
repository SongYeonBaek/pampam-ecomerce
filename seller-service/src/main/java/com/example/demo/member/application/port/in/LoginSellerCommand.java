package com.example.demo.member.application.port.in;

import com.example.demo.member.adapter.in.web.LoginSellerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginSellerCommand {
    private String email;
    private String password;

    public static LoginSellerCommand buildCommand(LoginSellerRequest request) {
        return LoginSellerCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
