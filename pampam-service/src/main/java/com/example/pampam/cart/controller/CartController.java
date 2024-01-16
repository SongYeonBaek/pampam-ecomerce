package com.example.pampam.cart.controller;

import com.example.pampam.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "/in/{productIdx}")
    public ResponseEntity<Object> cartIn(@AuthenticationPrincipal String email, @PathVariable Long productIdx) {
        return ResponseEntity.ok().body(cartService.cartIn(productIdx, email));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cartList")
    public ResponseEntity<Object> cartList(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok().body(cartService.cartList(email));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateCart/{cartIdx}")
    public ResponseEntity<Object> updateCart(@RequestHeader(value = "Authorization") String token, @PathVariable Long cartIdx) {
        return ResponseEntity.ok().body(cartService.updateCart(token, cartIdx));
    }
}