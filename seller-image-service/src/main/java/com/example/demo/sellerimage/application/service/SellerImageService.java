package com.example.demo.sellerimage.application.service;

import com.example.demo.sellerimage.application.port.in.SellerImageCommand;
import com.example.demo.sellerimage.application.port.in.SellerImageInport;
import com.example.demo.sellerimage.application.port.out.CreateEmailCertEventPort;
import com.example.demo.sellerimage.application.port.out.SellerImageOutport;
import com.example.demo.sellerimage.application.port.out.SellerImageUploadPort;
import com.example.demo.sellerimage.domain.SellerImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerImageService implements SellerImageInport {
    private final SellerImageOutport sellerImageOutport;
    private final SellerImageUploadPort sellerImageUploadPort;
    private final CreateEmailCertEventPort createEmailCertEventPort;

    @Override
    public SellerImage registerSellerImage(SellerImageCommand command) {
        String imagePath = sellerImageUploadPort.uploadSellerImage(command.getFile());
        SellerImage sellerImage = SellerImage.builder()
                .email(command.getEmail())
                .imagePath(imagePath)
                .build();

        createEmailCertEventPort.createEmailCertEvent(sellerImage);
        sellerImage = sellerImageOutport.registerSellerImage(sellerImage);



        return sellerImage;
    }
}
