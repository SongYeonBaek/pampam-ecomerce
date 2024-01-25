package com.example.demo.sellerimage.application.port.in;

import com.example.demo.sellerimage.domain.SellerImage;

public interface SellerImageInport {
    SellerImage registerSellerImage(SellerImageCommand command);
}
