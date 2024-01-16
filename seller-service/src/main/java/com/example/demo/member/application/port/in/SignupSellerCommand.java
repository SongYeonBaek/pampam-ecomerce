package com.example.demo.member.application.port.in;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class SignupSellerCommand {
    @NonNull
    private final String email;
    @NonNull
    private final String password;


    public SignupSellerCommand(String email, String password ) {
        this.email = email;
        this.password = password;

    }
}
