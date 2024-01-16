package com.example.pampam.product.model.response;

import com.example.pampam.product.model.entity.Product;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostProductResgisterRes {
        // 변동 가능
        private Boolean isSuccess;
        private Integer code;
        private String message;
        private Map<String,Long> result ;
        private Boolean success;

        // ToDo : 응답 설정하기
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
