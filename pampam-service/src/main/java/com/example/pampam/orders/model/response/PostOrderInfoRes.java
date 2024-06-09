package com.example.pampam.orders.model.response;

import com.example.pampam.orders.model.entity.Orders;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PostOrderInfoRes {
    private Long idx;
    private String impUid;
    private String productName;
    private LocalDate localDate;

    public static PostOrderInfoRes dtoToEntity(Long idx, String impUid, GetPortOneRes getPortOneRes, Orders orders) {
        return PostOrderInfoRes.builder()
                .idx(idx)
                .impUid(impUid)
                .productName(getPortOneRes.getName())
                .localDate(orders.getOrderDate())
                .build();
    }
}
