package com.example.pampam.product.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetProductReadRes2 {
    Boolean isSuccess;
    Integer code;
    String message;
    GetProductReadRes result;
    Boolean success;
}
