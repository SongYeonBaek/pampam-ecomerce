package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsumerDeleteRes {
    private String email;
}
