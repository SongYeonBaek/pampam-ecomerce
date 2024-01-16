package com.example.pampam.product.model.response;

import com.example.pampam.product.model.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class GetProductListRes {
    Boolean isSuccess;
    Integer code;
    String message;
    List<GetProductReadRes> result;
    Boolean success;
}
