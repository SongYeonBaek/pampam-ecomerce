package com.example.pampam.orders.model.response;

import com.example.pampam.orders.model.entity.Orders;
import com.example.pampam.product.model.entity.Product;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersListRes {
    private String impUid;
    private LocalDate orderDate;
    private String productName;
    private Integer amount;

    public static OrdersListRes dtoToEntity(Orders orders, Product product) {
        return OrdersListRes.builder()
                .impUid(orders.getImpUid())
                .orderDate(orders.getOrderDate())
                .productName(product.getProductName())
                .amount(orders.getPrice())
                .build();
    }
}
