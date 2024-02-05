package com.example.demo.sellerimage.adapter.in.web;

import com.example.demo.sellerimage.application.port.in.SellerImageCommand;
import com.example.demo.sellerimage.application.port.in.SellerImageInport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Component
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class SellerImageController {
    private final SellerImageInport sellerImageInport;
@RequestMapping(method = RequestMethod.POST, value = "/seller/image")
    public ResponseEntity registerSellerImage(@RequestPart MultipartFile file, String email){
        SellerImageCommand command = SellerImageCommand.builder()
                .email(email)
                .file(file)
                .build();
        return ResponseEntity.ok().body(sellerImageInport.registerSellerImage(command));
    }
}
