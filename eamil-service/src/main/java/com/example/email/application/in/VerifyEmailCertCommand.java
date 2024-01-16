package com.example.email.application.in;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VerifyEmailCertCommand {
    private String email;

    private String uuid;

    public VerifyEmailCertCommand(String email, String uuid) {
        this.email = email;
        this.uuid = uuid;
    }
}
