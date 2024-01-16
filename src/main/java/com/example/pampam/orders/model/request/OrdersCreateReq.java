package com.example.pampam.orders.model.request;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrdersCreateReq {
    private List<Long> productIdxList = new ArrayList<>();
}
