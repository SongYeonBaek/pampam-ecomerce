package com.example.pampam.orders.model.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortoneReq {
    private Long id;
    private String name;
    private Integer price;
}
