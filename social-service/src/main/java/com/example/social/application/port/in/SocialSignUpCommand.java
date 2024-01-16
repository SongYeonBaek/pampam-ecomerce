package com.example.social.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SocialSignUpCommand {
    private String code;
}
