package com.example.pampam.orders.controller;

import com.example.pampam.common.BaseResponse;
import com.example.pampam.orders.model.response.OrdersListRes;
import com.example.pampam.orders.model.response.PostOrderInfoRes;
import com.example.pampam.orders.service.OrdersService;
import com.example.pampam.orders.service.PaymentService;
import com.example.pampam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {
    private final OrdersService ordersService;
    private final PaymentService paymentService;
    private final ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST,value = "/validation")
    public BaseResponse<List<PostOrderInfoRes>> ordersCreate(@AuthenticationPrincipal String email, String impUid){
        try{
            //주문의 유효성 검사
            if(paymentService.paymentValidation(impUid)){
                //orders과 orderedProduct에 저장

                return ordersService.createOrder(email, impUid);
            }
            return BaseResponse.failResponse(7000, "결제가 유효하지 않아 주문 취소.");
        } catch (Exception e){
            return BaseResponse.failResponse(7000, "주문 취소.");
        }
    }

    //Consumer의 주문 내역을 확인
    @RequestMapping(value = "/list")
    public BaseResponse<List<OrdersListRes>>  orderList(@AuthenticationPrincipal String email) {
        return ordersService.orderList(email);
    }

    //Consumer가 구매를 취소
    @RequestMapping(method = RequestMethod.POST,value = "/cancel")
    public BaseResponse<String> orderCancel(String impUid) throws IOException {
        return paymentService.paymentCancel(impUid);
    }

    //마감 시간이 지나고 인원 수가 다 차지 않았다면 결제를 취소
    @RequestMapping(method = RequestMethod.POST,value = "/group/cancel")
    public BaseResponse<String> groupCancel(Long productId) throws IOException {
        return ordersService.groupCancel(productId);
    }

}
