package com.example.demo.member.application.port.in;

import com.example.demo.member.adapter.in.web.SignupSellerRequest;
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

    public static SignupSellerCommand buildCommand(SignupSellerRequest request, MultipartFile file) {
        return SignupSellerCommand.builder()
                .email(request.getEmail())
                .sellerPW(request.getSellerPW())
                .sellerName(request.getSellerName())
                .sellerAddr(request.getSellerAddr())
                .sellerPhoneNum(request.getSellerPhoneNum())
                .sellerBusinessNumber(request.getSellerBusinessNumber())
                .file(file)
                .build();
    }

}
