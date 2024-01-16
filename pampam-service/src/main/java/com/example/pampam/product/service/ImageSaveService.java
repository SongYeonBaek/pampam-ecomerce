package com.example.pampam.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.product.model.entity.ProductImage;
import com.example.pampam.product.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageSaveService {

    private final AmazonS3 s3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    private final ProductImageRepository productImageRepository;

    public String uploadFile(MultipartFile file) {

        String originalName = file.getOriginalFilename();
        String time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = time.replace("/", File.separator);
        String uuid = UUID.randomUUID().toString();
        String savedFileName = folderPath + File.separator + uuid + "_" + originalName;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            s3.putObject(bucket, savedFileName.replace(File.separator, "/"), file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return s3.getUrl(bucket, savedFileName.replace(File.separator, "/")).toString();
    }

    public void saveFile(Long sellerIdx, String uploadPath) {
        productImageRepository.save(ProductImage.builder()
                .product(Product.builder().idx(sellerIdx).build())
                .imagePath(uploadPath)
                .build());
    }
}

