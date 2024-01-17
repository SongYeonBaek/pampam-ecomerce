package com.example.pampam.cart.controller;

import com.example.pampam.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "장바구니 컨트롤러 v1", tags = "장바구니 API")
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "장바구니 넣기")
    @RequestMapping(method = RequestMethod.POST, value = "/in/{productIdx}")
    public ResponseEntity<Object> cartIn(@AuthenticationPrincipal String email, @PathVariable Long productIdx) {
        return ResponseEntity.ok().body(cartService.cartIn(productIdx, email));
    }
    @ApiOperation(value = "장바구니 목록 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/cartList")
    public ResponseEntity<Object> cartList(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok().body(cartService.cartList(email));
    }
    @ApiOperation(value = "장바구니 수정")
    @RequestMapping(method = RequestMethod.POST, value = "/updateCart/{cartIdx}")
    public ResponseEntity<Object> updateCart(@RequestHeader(value = "Authorization") String token, @PathVariable Long cartIdx) {
        return ResponseEntity.ok().body(cartService.updateCart(token, cartIdx));
    }
}