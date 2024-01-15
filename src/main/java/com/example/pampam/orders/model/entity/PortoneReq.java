package com.example.pampam.orders.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortoneReq {
    Long id;
    String name;
    Integer price;
}
