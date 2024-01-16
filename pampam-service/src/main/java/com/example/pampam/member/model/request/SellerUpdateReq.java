package com.example.pampam.member.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerUpdateReq {

    private String email;
    private String sellerPW;
    private String sellerName;
    private String sellerAddr;
    private String sellerPhoneNum;
    private String sellerBusinessNumber;

}
