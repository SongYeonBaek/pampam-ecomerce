package com.example.social.adapter.out.social;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginUserInfo {
    private String email;
    private String consumerName;
}
