package com.example.pampam.orders.model.entity;

import com.example.pampam.orders.model.response.GetPortOneRes;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProducts {
    private List<GetPortOneRes> products;
}
