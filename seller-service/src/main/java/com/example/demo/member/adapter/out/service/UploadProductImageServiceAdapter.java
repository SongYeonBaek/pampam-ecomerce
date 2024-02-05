package com.example.demo.member.adapter.out.service;

import com.example.demo.member.application.port.out.UploadSellerImagePort;
import com.example.demo.member.domain.SellerImage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UploadProductImageServiceAdapter implements UploadSellerImagePort {
    private final OpenFeignUploadSellerImage openFeignUploadSellerImage;


    @Override
    public void uploadSellerImagePort(SellerImage sellerImage) {
        try {

            openFeignUploadSellerImage.call(sellerImage.getFile(),sellerImage.getEmail() );

            System.gc();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
