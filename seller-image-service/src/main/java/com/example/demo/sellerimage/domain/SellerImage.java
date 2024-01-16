package com.example.demo.sellerimage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SellerImage {
    private final Long id;
    private final String email;
    private final String imagePath;
}
