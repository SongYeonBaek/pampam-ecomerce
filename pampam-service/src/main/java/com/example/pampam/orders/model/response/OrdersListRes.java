package com.example.pampam.orders.model.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersListRes {
    Long idx;
    LocalDate orderDate;
//    List<OrderedProduct> orderProductsList = new ArrayList<>();
    private String productName;
}
