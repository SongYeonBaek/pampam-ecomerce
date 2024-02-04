package com.example.demo.sellerimage.application.service;

import com.example.demo.common.UseCase;
import com.example.demo.sellerimage.application.port.in.SellerImageCommand;
import com.example.demo.sellerimage.application.port.in.SellerImageInport;
import com.example.demo.sellerimage.application.port.out.SellerImageOutport;
import com.example.demo.sellerimage.application.port.out.SellerImageUploadPort;
import com.example.demo.sellerimage.domain.SellerImage;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SellerImageService implements SellerImageInport {
    private final SellerImageOutport sellerImageOutport;
    private final SellerImageUploadPort sellerImageUploadPort;

    @Override
    public SellerImage registerSellerImage(SellerImageCommand command) {
        String imagePath = sellerImageUploadPort.uploadSellerImage(command.getFile());
        SellerImage sellerImage = SellerImage.builder()
                .email(command.getEmail())
                .imagePath(imagePath)
                .build();
        sellerImage = sellerImageOutport.registerSellerImage(sellerImage);


        return sellerImage;
    }
}
