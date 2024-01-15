package com.example.pampam.product.model.request;

import com.example.pampam.product.model.entity.ProductImage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
public class PatchProductUpdateReq {
    @NotBlank
    private Long id;
    private String productName;
    private Integer price;
    private Integer salePrice;
    private String productInfo;
    private List<ProductImage> productImages;
}