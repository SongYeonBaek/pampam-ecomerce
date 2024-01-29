package com.example.demo.member.application.port.in;

import lombok.Builder;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class SignupSellerCommand {
    private String email;
    private String sellerPW;
    private String sellerName;
    private String sellerAddr;
    private String sellerPhoneNum;
    private String sellerBusinessNumber;
    private MultipartFile file;

}
