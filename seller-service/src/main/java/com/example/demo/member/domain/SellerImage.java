package com.example.demo.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@AllArgsConstructor
public class SellerImage {
    private final String email;
    private final MultipartFile file;
}
