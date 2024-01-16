package com.example.pampam.orders.service;

import com.example.pampam.common.BaseResponse;
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
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.gson.Gson;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
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


    public BaseResponse<List<PostOrderInfoRes>> createOrder(String email, String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = paymentService.getPaymentInfo(impUid);
        Integer amount = iamportResponse.getResponse().getAmount().intValue();
        String customDataString = iamportResponse.getResponse().getCustomData();
        customDataString = "{\"products\":" + customDataString + "}";
        Gson gson = new Gson();
        PaymentProducts paymentProducts = gson.fromJson(customDataString, PaymentProducts.class);

        List<PostOrderInfoRes> orderList = new ArrayList<>();

        Optional<Consumer> result = consumerRepository.findByEmail(email);
        if(result.isPresent()) {
            Orders order = ordersRepository.save(Orders.builder()
                    .impUid(impUid)
                    .consumer(result.get())
                    .price(amount)
                    .orderDate(LocalDate.now())
                    .build());

            //Custom Data 안에 있던 Product 리스트 하나씩 꺼내와서 OrderedProduct에 저장
            for (GetPortOneRes portoneReq : paymentProducts.getProducts()) {
                orderedProductRepository.save(OrderedProduct.builder()
                        .orders(order)
                        .product(Product.builder().idx(portoneReq.getId()).build())
                        .build());
                orderList.add(PostOrderInfoRes.builder()
                        .impUid(portoneReq.getName())
                        .build());
            }
            return BaseResponse.successResponse("주문 완료", orderList);
        }
        return BaseResponse.failResponse("주문 에러");
    }

    public BaseResponse<List<OrdersListRes>> orderList(String email) {
        List<OrdersListRes> result = new ArrayList<>();
        Optional<Consumer> consumer = consumerRepository.findByEmail(email);
        if(consumer.isPresent()){
            List<Orders> ordersList = consumer.get().getOrdersList();
            for(Orders orders : ordersList){
                Product product = orders.getOrderProductsList().get(0).getProduct();
                result.add(OrdersListRes.builder()
                                .idx(orders.getIdx())
                                .orderDate(orders.getOrderDate())
                                .productName(product.getProductName())
                        .build());
            }
        }
        return BaseResponse.successResponse("주문 내역 조회.", result);
    }

    public BaseResponse<String> groupCancel(Long productId) throws IOException {
        Optional<Product> product = productRepository.findById(productId);

        List<String> impUidList = new ArrayList<>();
        if(product.isPresent()){
            for(OrderedProduct p : product.get().getOrderedProducts()){
                impUidList.add(p.getOrders().getImpUid());
            }
        }

        for(String impUid : impUidList){
            paymentService.paymentCancel(impUid);
        }


        return  BaseResponse.successResponse("공동구매 전원 취소 완료", "[결제 취소] 인원 부족으로 인해 공동구매가 취소되었습니다.");
    }
}
