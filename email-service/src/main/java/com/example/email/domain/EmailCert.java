package com.example.email.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmailCert {
    private final Long idx;
    private final String email;
    private final String uuid;
    private final Boolean status;
}
