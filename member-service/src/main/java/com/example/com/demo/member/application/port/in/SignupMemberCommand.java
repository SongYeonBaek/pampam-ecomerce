package com.example.com.demo.member.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class SignupMemberCommand {

    private final String email;
    private final String password;


    public SignupMemberCommand(String email, String password ) {
        this.email = email;
        this.password = password;

    }
}
