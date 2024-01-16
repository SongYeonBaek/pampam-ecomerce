package com.example.pampam.orders.model.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersListRes {
    private Long idx;
    private LocalDate orderDate;
    private String productName;
}
