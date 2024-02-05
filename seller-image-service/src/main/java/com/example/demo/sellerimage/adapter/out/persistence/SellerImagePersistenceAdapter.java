package com.example.demo.sellerimage.adapter.out.persistence;

import com.example.demo.sellerimage.application.port.out.SellerImageOutport;
import com.example.demo.sellerimage.domain.SellerImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerImagePersistenceAdapter implements SellerImageOutport {
    private final SpringDataSellerImageRepository imageRepository;
    @Override
    public SellerImage registerSellerImage(SellerImage sellerImage) {
        SellerImageJpaEntity sellerImageJpaEntity = SellerImageJpaEntity.builder()
                .email(sellerImage.getEmail())
                .imagePath(sellerImage.getImagePath())
                .build();

        sellerImageJpaEntity = imageRepository.save(sellerImageJpaEntity);
        return SellerImage.builder()
                .id(sellerImageJpaEntity.getId())
                .email(sellerImageJpaEntity.getEmail())
                .imagePath(sellerImageJpaEntity.getImagePath())
                .build();
    }
}
