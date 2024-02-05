package com.example.demo.member.adapter.out.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@FeignClient(name = "ProductImage", url = "http://3.35.233.140:7030/seller", configuration = CommonsMultipartResolver.class)
public interface OpenFeignUploadSellerImage {

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void call(@RequestPart MultipartFile file, @RequestParam String email);
}