package com.example.pampam.member.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerUpdateRes {
    private Long sellerIdx;
    private String sellerPW;
    private String sellerName;
    private String sellerAddr;
    private String sellerPhoneNum;
    private String email;
    private String authority;
    private Boolean status;
    private String sellerBusinessNumber;
}
