package com.example.pampam.orders.service;

import com.example.pampam.common.BaseResponse;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.repository.ConsumerRepository;
import com.example.pampam.orders.model.entity.OrderedProduct;
import com.example.pampam.orders.model.entity.Orders;
import com.example.pampam.orders.model.entity.PaymentProducts;
import com.example.pampam.orders.model.response.GetPortOneRes;
import com.example.pampam.orders.model.response.OrdersListRes;
import com.example.pampam.orders.model.response.PostOrderInfoRes;
import com.example.pampam.orders.repository.OrderedProductRepository;
import com.example.pampam.orders.repository.OrdersRepository;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.product.repository.ProductRepository;
import com.example.pampam.utils.JwtUtils;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.gson.Gson;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final PaymentService paymentService;
    private final ConsumerRepository consumerRepository;
    private final ProductRepository productRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse<List<PostOrderInfoRes>> createOrder(String token, String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = paymentService.getPaymentInfo(impUid);
        Integer amount = iamportResponse.getResponse().getAmount().intValue();
        String customDataString = iamportResponse.getResponse().getCustomData();
        customDataString = "{\"products\":" + customDataString + "}";
        Gson gson = new Gson();
        PaymentProducts paymentProducts = gson.fromJson(customDataString, PaymentProducts.class);

        List<PostOrderInfoRes> orderList = new ArrayList<>();

        Optional<Consumer> result = consumerRepository.findByEmail(token);
        if(result.isPresent()) {
            Orders order = ordersRepository.save(Orders.dtoToEntity(impUid, token, amount));

            //Custom Data 안에 있던 Product 리스트 하나씩 꺼내와서 OrderedProduct에 저장
            for (GetPortOneRes getPortOneRes : paymentProducts.getProducts()) {
                orderedProductRepository.save(OrderedProduct.dtoToEntity(order, getPortOneRes));

                orderList.add(PostOrderInfoRes.dtoToEntity(impUid, getPortOneRes, order));
            }
            return BaseResponse.successResponse("주문 완료", orderList);
        }
        throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
    }

    public BaseResponse<List<OrdersListRes>> orderList(String token) {
        token = JwtUtils.replaceToken(token);
        Claims consumerInfo = JwtUtils.getConsumerInfo(token, secretKey);
        String email = consumerInfo.get("email", String.class);
        List<OrdersListRes> result = new ArrayList<>();
        if (email != null) {
            List<Orders> orders = ordersRepository.findAllByConsumerEmail(email);
            for(Orders order :orders){
                Product product = order.getOrderProductsList().get(0).getProduct();
                result.add(OrdersListRes.dtoToEntity(order, product));
            }

            return BaseResponse.successResponse("주문 내역 조회.", result);
        }
        return null;
    }

    public BaseResponse<String> groupCancel(Long productId) throws IOException {
        Optional<Product> product = productRepository.findById(productId);

        List<String> impUidList = new ArrayList<>();
        if(product.isPresent()){
            for(OrderedProduct p : product.get().getOrderedProducts()){
                impUidList.add(p.getOrders().getImpUid());
            }
        } else {
            throw new EcommerceApplicationException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        for(String impUid : impUidList){
            paymentService.paymentCancel(impUid);
        }
        return  BaseResponse.successResponse("공동구매 전원 취소 완료", "[결제 취소] 인원 부족으로 인해 공동구매가 취소되었습니다.");
    }
}
