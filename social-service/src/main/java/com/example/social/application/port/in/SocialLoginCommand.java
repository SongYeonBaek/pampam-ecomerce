package com.example.social.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SocialLoginCommand {
    private String email;
}
