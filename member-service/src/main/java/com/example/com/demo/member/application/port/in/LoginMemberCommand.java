package com.example.com.demo.member.application.port.in;

import com.example.com.demo.member.adapter.in.web.LoginMemberRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginMemberCommand {
    private String email;
    private String password;

    public static LoginMemberCommand buildCommand(LoginMemberRequest request) {
        return LoginMemberCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
