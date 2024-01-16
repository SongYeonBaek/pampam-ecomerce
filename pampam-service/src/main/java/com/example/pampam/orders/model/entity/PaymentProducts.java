package com.example.pampam.orders.model.entity;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProducts {
    private List<PortoneReq> products;
}
