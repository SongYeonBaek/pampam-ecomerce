package com.example.pampam.orders.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PostOrderInfoRes {
    private String impUid;
    private String productName;
    private LocalDate localDate;
}
