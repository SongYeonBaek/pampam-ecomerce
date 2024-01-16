package com.example.pampam.orders.controller;

import com.example.pampam.orders.service.OrdersService;
import com.example.pampam.orders.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {
    private final OrdersService ordersService;
    private final PaymentService paymentService;

    @RequestMapping(method = RequestMethod.POST,value = "/validation")
    public ResponseEntity<String> ordersCreate(@AuthenticationPrincipal String email, String impUid){
        try{
            //주문의 유효성 검사
            if(paymentService.paymentValidation(impUid)){
                //orders과 orderedProduct에 저장
                ordersService.createOrder(email, impUid);

                return ResponseEntity.ok().body("ok");
            }
            return ResponseEntity.ok().body("error");
        } catch (Exception e){
            return null;
        }
    }

    //Consumer의 주문 내역을 확인
    @RequestMapping(value = "/list")
    public ResponseEntity<Object> orderList(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok().body(ordersService.orderList(email));
    }

    //마감 시간이 지나고 인원 수가 다 차지 않았다면 결제를 취소
    @RequestMapping(method = RequestMethod.POST,value = "/cancel")
    public void paymentCancel(String impUid) throws IOException {
        paymentService.paymentCancel(impUid);
    }

}
