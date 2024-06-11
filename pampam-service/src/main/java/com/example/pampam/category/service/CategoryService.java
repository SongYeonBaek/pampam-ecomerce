package com.example.pampam.category.service;

import com.example.pampam.category.model.entity.Category;
import com.example.pampam.category.model.request.PostRegisterCategory;
import com.example.pampam.category.model.response.GetCategoriesRes;
import com.example.pampam.category.model.response.GetSearchProductToCategory;
import com.example.pampam.category.repository.CategoryRepository;
import com.example.pampam.common.BaseResponse;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.product.repository.ProductRepository;
import com.example.pampam.utils.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public BaseResponse<Object> searchRegion(String categoryType) {
        List<Category> categoryOfProduct = categoryRepository.findAllByCategoryType(categoryType);
        List<GetSearchProductToCategory> productList = new ArrayList<>();
        for (Category category : categoryOfProduct) {
            productList.add(GetSearchProductToCategory.buildProductToCategory(category.getProduct()));
        }

        return BaseResponse.successResponse("카테고리 조회 성공", productList);
    }

    public BaseResponse<Object> insertCategory(Long idx, PostRegisterCategory categoryInfo) {
        Optional<Product> product = productRepository.findById(idx);
        if (product.isPresent()) {
            product.get().setCategory(Category.buildCategory(categoryInfo.getCategoryType()));
            return BaseResponse.successResponse("요청 성공", "카테고리 등록 완료");
        } else {
            throw new EcommerceApplicationException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }
}
