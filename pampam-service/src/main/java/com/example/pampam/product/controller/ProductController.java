package com.example.pampam.product.controller;

import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.product.model.request.PatchProductUpdateReq;
import com.example.pampam.product.model.request.PostProductRegisterReq;
import com.example.pampam.product.service.ProductService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(value = "상품 컨트롤러 v1", tags = "상품 API")
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;
    @ApiOperation(value = "상품 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
                    required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "productRegisterReq", paramType = "query", value = "등록할 상품 정보", required = true)
    })
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Object> register(@RequestHeader(value = "Authorization") String token, @RequestPart PostProductRegisterReq productRegisterReq, @RequestPart MultipartFile[] images) {
        return ResponseEntity.ok().body(productService.register(token, productRegisterReq, images));
    }

    @ApiOperation(value = "상품 목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
                    required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "page", value = "페이지 번호",
                    required = true, paramType = "query", dataType = "Integer", defaultValue = ""),
            @ApiImplicitParam(name = "size", value = "조회 할 상품의 개수",
                    required = true, dataType = "Integer", paramType = "query", defaultValue = "")})
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<Object> list(@RequestHeader(value = "Authorization") String token, Integer page, Integer size) {
        return ResponseEntity.ok().body(productService.list(token,page, size));
    }

    @ApiOperation(value = "상품 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
            required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "idx", value = "조회 할 상품 번호",
                    required = true, paramType = "query", dataType = "Long", defaultValue = "")})
    @GetMapping("/read/{idx}")
    public ResponseEntity<Object> read(@RequestHeader(value = "Authorization") String token, @PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.read(token,idx));
    }

    //RequestPart => 포스트맨에서 multipart/form-data가 포함되어 있는 경우에 사용/ Json 데이터의 입력을 위함
    @ApiOperation(value = "상품 수정")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
            required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "patchProductUpdateReq", paramType = "query", value = "수정할 상품 정보", required = true)

    })
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<Object> update(@RequestHeader(value = "Authorization") String token, @RequestPart PatchProductUpdateReq patchProductUpdateReq) {
        return ResponseEntity.ok().body(productService.update(token,patchProductUpdateReq));
    }

    @ApiOperation(value = "상품 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
            required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "idx", value = "삭제할 상품 번호",
                    required = true, paramType = "query", dataType = "Long", defaultValue = "")})
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{idx}")
    public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization") String token, @PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.delete(token,idx));
    }
}
