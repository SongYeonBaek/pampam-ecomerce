package com.example.pampam.orders.service;

import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.repository.ConsumerRepository;
import com.example.pampam.orders.model.entity.OrderedProduct;
import com.example.pampam.orders.model.entity.Orders;
import com.example.pampam.orders.model.entity.PaymentProducts;
import com.example.pampam.orders.model.entity.PortoneReq;
import com.example.pampam.orders.model.response.OrdersListRes;
import com.example.pampam.orders.repository.OrderedProductRepository;
import com.example.pampam.orders.repository.OrdersRepository;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.product.repository.ProductRepository;
import com.google.gson.Gson;
import com.siot.IamportRestClient.IamportClient;
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
    private final ProductRepository productRepository;
    private final IamportClient iamportClient;
    private final ConsumerRepository consumerRepository;


    //결제 정보를 가져옴. IamportResponse에 response 다 들어있음! (amount, customData 등)
    public IamportResponse getPaymentInfo(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);
        return response;
    }

    //주문한 총 가격을 구하는 메소드
    public Integer getTotalPrice(List<PortoneReq> datas){
        List<Long> productIds = new ArrayList<>();
        for (PortoneReq product: datas) {
            productIds.add(product.getId());
        }

        List<Product> products = productRepository.findAllById(productIds);

        Integer totalPrice = 0;
        for (PortoneReq product: datas) {
            totalPrice += product.getPrice();
        }

        return  totalPrice;
    }

    //Portone에서 결제 정보 가져와서 검증 처리
    public Boolean paymentValidation(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(impUid);
        Integer amount = response.getResponse().getAmount().intValue();
        String customDataString = response.getResponse().getCustomData();
        System.out.println(customDataString);
        customDataString = "{\"products\":" + customDataString + "}";
        Gson gson = new Gson();
        PaymentProducts paymentProducts = gson.fromJson(customDataString, PaymentProducts.class);

        Integer totalPrice = getTotalPrice(paymentProducts.getProducts());
        if(amount.equals(totalPrice) ) {
            return true;
        }
        return false;
    }

    public void createOrder(String email, String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = getPaymentInfo(impUid);
        Integer amount = iamportResponse.getResponse().getAmount().intValue();
        String customDataString = iamportResponse.getResponse().getCustomData();
        customDataString = "{\"products\":" + customDataString + "}";
        Gson gson = new Gson();
        PaymentProducts paymentProducts = gson.fromJson(customDataString, PaymentProducts.class);

        Optional<Consumer> result = consumerRepository.findByEmail(email);
        if(result.isPresent()) {
            Orders order = ordersRepository.save(Orders.builder()
                    .impUid(impUid)
                    .consumer(result.get())
                    .price(amount)
                    .orderDate(LocalDate.now())
                    .build());

            //Custom Data 안에 있던 Product 리스트 하나씩 꺼내와서 OrderedProduct에 저장
            for (PortoneReq portoneReq : paymentProducts.getProducts()) {
                orderedProductRepository.save(OrderedProduct.builder()
                        .orders(order)
                        .product(Product.builder().idx(portoneReq.getId()).build())
                        .build());
            }
        }
    }

    public List<OrdersListRes> orderList(String email) {
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
        return result;
    }
}
