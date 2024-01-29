package com.example.demo.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Seller {
    private final Long id;
    private final String email;
    private final String sellerPW;
    private final String sellerName;
    private final String sellerAddr;
    private final String sellerPhoneNum;
    private final String sellerBusinessNumber;
    private final String authority;

}
