package com.example.pampam.orders.controller;

import com.example.pampam.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {
    private final OrdersService ordersService;

    @RequestMapping(method = RequestMethod.POST,value = "/validation")
    public ResponseEntity<String> ordersCreate(@AuthenticationPrincipal String email, String impUid){
        try{
            if(ordersService.paymentValidation(impUid)){

                //order 데이터 저장, orderedProduct 데이터 저장
                ordersService.createOrder(email, impUid);

                return ResponseEntity.ok().body("ok");
            }
            return ResponseEntity.ok().body("error");
        } catch (Exception e){
            return null;
        }
    }

    //Consumer가 구매한 주문 내역을 리스트로 확인한다.
    @RequestMapping(value = "/list")
    public ResponseEntity<Object> orderList(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok().body(ordersService.orderList(email));
    }

    //Seller가 파는 해당 상품에 대해 주문된 리스트를 확인한다.
//    @RequestMapping(method = RequestMethod.GET, value = "/order/productlist")
//    public ResponseEntity productList() {
//        return ResponseEntity.ok().body(ordersService.productList());
//    }

}
