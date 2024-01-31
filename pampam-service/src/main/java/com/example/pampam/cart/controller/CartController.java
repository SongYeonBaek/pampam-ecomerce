package com.example.pampam.cart.controller;

import com.example.pampam.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "장바구니 컨트롤러 v1", tags = "장바구니 API")
@RequiredArgsConstructor
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "장바구니 넣기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
                    required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "productIdx", value = "장바구니에 넣을 상품 번호 입력",
                    required = true, paramType = "query", dataType = "Long", defaultValue = "")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/in/{productIdx}")
    public ResponseEntity<Object> cartIn(@RequestHeader(value = "Authorization") String token, @PathVariable Long productIdx) {
        return ResponseEntity.ok().body(cartService.cartIn(productIdx, token));
    }

    @ApiOperation(value = "장바구니 목록 조회")
    @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
            required = true, paramType = "query", dataType = "string", defaultValue = "")
    @RequestMapping(method = RequestMethod.GET, value = "/cartList")
    public ResponseEntity<Object> cartList(@RequestHeader(value = "Authorization") String token) {
        return ResponseEntity.ok().body(cartService.cartList(token));
    }

    @ApiOperation(value = "장바구니 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
                    required = true, paramType = "query", dataType = "string", defaultValue = ""),
            @ApiImplicitParam(name = "cartIdx", value = "카트 번호 입력",
                    required = true, paramType = "query", dataType = "Long", defaultValue = "") })
    @RequestMapping(method = RequestMethod.POST, value = "/updateCart/{cartIdx}")
    public ResponseEntity<Object> updateCart(@RequestHeader(value = "Authorization") String token, @PathVariable Long cartIdx) {
        return ResponseEntity.ok().body(cartService.updateCart(token, cartIdx));
    }
}