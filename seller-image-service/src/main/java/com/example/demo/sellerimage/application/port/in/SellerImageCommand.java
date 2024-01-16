package com.example.demo.sellerimage.application.port.in;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class SellerImageCommand {
    private final String email;
    private final MultipartFile file;
}
