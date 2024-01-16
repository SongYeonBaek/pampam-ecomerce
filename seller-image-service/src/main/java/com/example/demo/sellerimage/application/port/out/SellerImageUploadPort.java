package com.example.demo.sellerimage.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface SellerImageUploadPort {
    String uploadSellerImage(MultipartFile file);
}
