package com.example.pampam.product.controller;

import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.product.model.request.PatchProductUpdateReq;
import com.example.pampam.product.model.request.PostProductRegisterReq;
import com.example.pampam.product.model.response.PostProductResgisterRes;
import com.example.pampam.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(value = "상품 컨트롤러 v1", tags = "상품 API")
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @ApiOperation(value = "상품 등록")
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Object> register(@AuthenticationPrincipal String email, @RequestPart PostProductRegisterReq productRegisterReq, @RequestPart MultipartFile[] images) {
        if (email == null) {
            throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
        }
        return ResponseEntity.ok(productService.register(email,productRegisterReq,images));
    }
    @ApiOperation(value = "상품 목록 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<Object> list(@AuthenticationPrincipal String email,Integer page, Integer size) {
        return ResponseEntity.ok().body(productService.list(email,page, size));
    }
    @ApiOperation(value = "상품 조회")
    @GetMapping("/read/{idx}")
    public ResponseEntity<Object> read(@AuthenticationPrincipal String email,@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.read(email,idx));
    }

    //RequestPart => 포스트맨에서 multipart/form-data가 포함되어 있는 경우에 사용/ Json 데이터의 입력을 위함
    @ApiOperation(value = "상품 수정")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<Object> update(@AuthenticationPrincipal String email, @RequestPart PatchProductUpdateReq patchProductUpdateReq) {
        return ResponseEntity.ok().body(productService.update(email,patchProductUpdateReq));
    }

    @ApiOperation(value = "상품 삭제")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{idx}")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal String email,@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.delete(email,idx));
    }
}
