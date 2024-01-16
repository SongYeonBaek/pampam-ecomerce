package com.example.demo.sellerimage.application.port.out;


import com.example.demo.sellerimage.domain.SellerImage;

public interface CreateEmailCertEventPort {
    void createEmailCertEvent(SellerImage sellerImage);
}
