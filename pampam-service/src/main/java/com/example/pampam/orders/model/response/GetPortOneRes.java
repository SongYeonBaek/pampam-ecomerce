package com.example.pampam.orders.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOneRes {
    private Long id;
    private String name;
    private Integer price;
}
