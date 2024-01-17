package com.example.pampam.orders.controller;

import com.example.pampam.common.BaseResponse;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.orders.model.response.OrdersListRes;
import com.example.pampam.orders.model.response.PostOrderInfoRes;
import com.example.pampam.orders.service.OrdersService;
import com.example.pampam.orders.service.PaymentService;
import com.example.pampam.product.repository.ProductRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Api(value = "주문/결제 컨트롤러 v1", tags = "주문/결제 API")
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {
    private final OrdersService ordersService;
    private final PaymentService paymentService;

    @ApiOperation(value = "상품 주문")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "impUid", value = "주문 번호 입력",
                    required = true, dataType = "string", paramType = "query", defaultValue = ""))
    @RequestMapping(method = RequestMethod.POST,value = "/validation")
    public BaseResponse<List<PostOrderInfoRes>> ordersCreate(@AuthenticationPrincipal String email, String impUid){
        try{
            //주문의 유효성 검사
            if(paymentService.paymentValidation(impUid)){
                //orders과 orderedProduct에 저장

                return ordersService.createOrder(email, impUid);
            }
            throw new EcommerceApplicationException(ErrorCode.NOT_MATCH_AMOUNT);
        } catch (Exception e){
            throw new EcommerceApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //Consumer의 주문 내역을 확인
    @ApiOperation(value = "주문 내역 조회")
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public BaseResponse<List<OrdersListRes>>  orderList(@AuthenticationPrincipal String email) {
        return ordersService.orderList(email);
    }

    //Consumer가 구매를 취소
    @ApiOperation(value = "주문 취소")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "impUid", value = "취소할 주문의 주문 번호 입력",
                    required = true, dataType = "string", paramType = "query", defaultValue = ""))
    @RequestMapping(method = RequestMethod.GET,value = "/cancel")
    public BaseResponse<String> orderCancel(String impUid) throws IOException {
        return paymentService.paymentCancel(impUid);
    }

    //마감 시간이 지나고 인원 수가 다 차지 않았다면 결제를 취소
    @ApiOperation(value = "공동 구매 전체 취소")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "productId", value = "공동 구매를 취소할 상품의 상품 번호 입력",
                    required = true, dataType = "string", paramType = "query", defaultValue = ""))

    @RequestMapping(method = RequestMethod.GET,value = "/group/cancel")
    public BaseResponse<String> groupCancel(Long productId) throws IOException {
        return ordersService.groupCancel(productId);
    }
}
