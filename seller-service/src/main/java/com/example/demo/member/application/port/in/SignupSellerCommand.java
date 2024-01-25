package com.example.demo.member.application.port.in;

import lombok.Builder;
import lombok.Data;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class SignupSellerCommand {
    @NonNull
    private String email;
    @NonNull
    private String sellerPW;
    @NonNull
    private String sellerName;
    @NonNull
    private String sellerAddr;
    @NonNull
    private String sellerPhoneNum;
    @NonNull
    private String sellerBusinessNumber;
    @NonNull
    private MultipartFile file;

    public SignupSellerCommand(@NonNull MultipartFile file,@NonNull String email, @NonNull String sellerPW, @NonNull String sellerName, @NonNull String sellerAddr, @NonNull String sellerPhoneNum, @NonNull String sellerBusinessNumber) {
        this.email = email;
        this.sellerPW = sellerPW;
        this.sellerName = sellerName;
        this.sellerAddr = sellerAddr;
        this.sellerPhoneNum = sellerPhoneNum;
        this.sellerBusinessNumber = sellerBusinessNumber;
        this.file = file;
    }
}
