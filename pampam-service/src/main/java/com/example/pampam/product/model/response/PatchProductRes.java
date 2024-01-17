package com.example.pampam.product.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
public class PatchProductRes {
    private Long productIdx;
}
