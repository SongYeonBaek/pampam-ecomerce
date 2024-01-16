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

    public static PostProductResgisterRes EntityToDto(Product product)
    {
        Map<String,Long> result2 = new HashMap<>();
        result2.put("idx",product.getIdx());
        PostProductResgisterRes postProductResgisterRes = PostProductResgisterRes.builder()
                .isSuccess(true)
                .code(1000)
                .message("요청 성공.")
                .result(result2)
                .success(true)
                .build();
        return postProductResgisterRes;
    }
}
