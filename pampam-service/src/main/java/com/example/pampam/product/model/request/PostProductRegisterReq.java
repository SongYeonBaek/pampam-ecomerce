package com.example.pampam.product.model.request;

import com.example.pampam.product.model.entity.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class PostProductRegisterReq {
    @NotBlank(message = "상품 카테고리를 입력해주세요.")
    private ProductType productType;
    @NotBlank(message = "상품 이름을 입력해주세요.")
    private String productName;
    @NotBlank(message = "상품 가격을 입력해주세요.")
    private Integer price;
    @NotBlank(message = "상품 할인 가격을 입력해주세요.")
    private Integer salePrice;
    @NotBlank(message = "상품 상품 정보를 입력해주세요.")
    private String productInfo;
    private Integer people;
    @NotBlank(message = "공동구매 참여 인원 수를 입력해주세요.")
    private Integer peopleCount;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date startAt;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date closeAt;
}