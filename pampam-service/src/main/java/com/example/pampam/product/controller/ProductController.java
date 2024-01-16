package com.example.pampam.product.controller;

import com.example.pampam.product.model.request.PatchProductUpdateReq;
import com.example.pampam.product.model.request.PostProductRegisterReq;
import com.example.pampam.product.model.response.PostProductResgisterRes;
import com.example.pampam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register(@AuthenticationPrincipal String email, @RequestPart PostProductRegisterReq productRegisterReq, @RequestPart MultipartFile[] images) {
        return ResponseEntity.ok(productService.register(email,productRegisterReq,images));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(@AuthenticationPrincipal String email,Integer page, Integer size) {
        return ResponseEntity.ok().body(productService.list(email,page, size));
    }
    @GetMapping("/read/{idx}")
    public ResponseEntity read(@AuthenticationPrincipal String email,@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.read(email,idx));
    }

    //RequestPart => 포스트맨에서 multipart/form-data가 포함되어 있는 경우에 사용/ Json 데이터의 입력을 위함
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(@AuthenticationPrincipal String email, @RequestPart PatchProductUpdateReq patchProductUpdateReq) {
        productService.update(email,patchProductUpdateReq);

        return ResponseEntity.ok().body("수정");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{idx}")
    public ResponseEntity delete(@AuthenticationPrincipal String email,@PathVariable Long idx) {
        productService.delete(email,idx);
        return ResponseEntity.ok().body("삭제");

    }
}
