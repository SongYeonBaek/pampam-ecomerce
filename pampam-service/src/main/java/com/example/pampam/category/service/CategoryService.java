package com.example.pampam.category.service;

import com.example.pampam.category.model.entity.Category;
import com.example.pampam.category.model.entity.CategoryToProduct;
import com.example.pampam.category.model.request.PostInsertCategoryReq;
import com.example.pampam.category.model.response.GetSearchRes;
import com.example.pampam.category.model.response.PostInsertRes;
import com.example.pampam.category.repository.CategoryRepository;
import com.example.pampam.category.repository.CategoryToProductRepository;
import com.example.pampam.common.BaseResponse;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.product.repository.ProductRepository;
import com.example.pampam.utils.ProductType;
import com.example.pampam.utils.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToProductRepository categoryToProductRepository;
    private final ProductRepository productRepository;

    public Object searchRegion(String region) {
        List<CategoryToProduct> category = categoryToProductRepository.findALlByCategory(Category.builder().region(region).build());
        List<GetSearchRes> searchList = new ArrayList<>();

        for (CategoryToProduct categoryToProduct : category) {

            if (categoryToProduct == null) {
                throw new EcommerceApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
            }

            searchList.add(GetSearchRes.entityToDto(categoryToProduct));
        }

        return BaseResponse.successResponse("카테고리 조회 성공", searchList);
    }

    public BaseResponse<PostInsertRes> insertCategory(Long idx, PostInsertCategoryReq categoryReq) {
        Optional<Product> product = productRepository.findById(idx);

        if (product.isPresent()) {
            Category category = categoryRepository.save(Category.dtoToEntity(categoryReq));
            categoryToProductRepository.save(CategoryToProduct.dtoToEntity(category, product.get()));
            PostInsertRes postInsertRes = PostInsertRes.entityToDto(product.get(), category);

            return BaseResponse.successResponse("요청 성공", postInsertRes);
        } else {
            throw new EcommerceApplicationException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }
}
