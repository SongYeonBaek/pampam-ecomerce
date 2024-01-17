package com.example.email.application.in;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class CreateEmailCertCommand {
    private String email;
    private String uuid;
}
