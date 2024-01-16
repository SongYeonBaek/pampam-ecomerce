package com.example.pampam.product.service;

import com.example.pampam.common.BaseResponse;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.member.model.entity.Seller;
import com.example.pampam.member.repository.SellerRepository;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.product.model.entity.ProductImage;
import com.example.pampam.product.model.request.PatchProductUpdateReq;
import com.example.pampam.product.model.request.PostProductRegisterReq;
import com.example.pampam.product.model.response.GetProductReadRes;
import com.example.pampam.product.model.response.PostProductResgisterRes;
import com.example.pampam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageSaveService imageSaveService;
    private final SellerRepository sellerRepository;

    // TODO: 상품 등록
    public BaseResponse<PostProductResgisterRes> register(String email, PostProductRegisterReq productRegisterReq, MultipartFile[] images) {

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        Seller user;

        if (seller.isPresent()) {
            user = seller.get();
        } else throw new EcommerceApplicationException(
                ErrorCode.USER_NOT_FOUND, String.format("%s을 찾을 수 없습니다.", email), ErrorCode.USER_NOT_FOUND.getCode());

        Product product;
        product = productRepository.save(Product.builder()
                .productName(productRegisterReq.getProductName())
                .productInfo(productRegisterReq.getProductInfo())
                .price(productRegisterReq.getPrice())
                .salePrice(productRegisterReq.getSalePrice())
                .startAt(productRegisterReq.getStartAt())
                .closeAt(productRegisterReq.getCloseAt())
                .people(productRegisterReq.getPeople())
                .peopleCount(productRegisterReq.getPeopleCount())
                .sellerIdx(user)
                .build());

        for (MultipartFile uploadFile : images) {
            String uploadPath = imageSaveService.uploadFile(uploadFile);
            imageSaveService.saveFile(product.getIdx(), uploadPath);
        }


        Long result = product.getIdx();
        PostProductResgisterRes postProductResgisterRes = PostProductResgisterRes.builder()
                .idx(result)
                .build();

        return BaseResponse.successResponse("요청 성공", postProductResgisterRes);
    }

    // TODO: 상품 전체 조회
    public BaseResponse<Object> list(String email, Integer page, Integer size) {

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if (seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new EcommerceApplicationException(
                ErrorCode.USER_NOT_FOUND, String.format("%s을 찾을 수 없습니다.", email), ErrorCode.USER_NOT_FOUND.getCode());

        Pageable pageable = PageRequest.of(page-1,size);
        Page<Product> result = productRepository.findList(pageable);

        List<GetProductReadRes> productReadResList = new ArrayList<>();

        for (Product product : result.getContent()) {

            List<ProductImage> productImages = product.getImages();

            String filenames = "";
            for (ProductImage productImage : productImages) {
                String filename = productImage.getImagePath();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            GetProductReadRes getProductReadRes = GetProductReadRes.builder()
                    .idx(product.getIdx())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .salePrice(product.getSalePrice())
                    .productInfo(product.getProductInfo())
                    .filename(filenames)
                    .sellerIdx(product.getSellerIdx())
                    .peopleCount(product.getPeopleCount())
                    .startAt(product.getStartAt())
                    .closeAt(product.getCloseAt())
                    .build();
            productReadResList.add(getProductReadRes);
        }
        // DtoToRes
        return BaseResponse.successResponse("요청 성공", productReadResList);
    }

    public BaseResponse<GetProductReadRes> read(String email, Long idx) {
        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new EcommerceApplicationException(
                ErrorCode.USER_NOT_FOUND, String.format("%s을 찾을 수 없습니다.", email), ErrorCode.USER_NOT_FOUND.getCode());

        Optional<Product> result = productRepository.findById(idx);

        if (result.isPresent()) {
            Product product = result.get();

            List<ProductImage> productImages = product.getImages();

            String filenames = "";
            for (ProductImage productImage : productImages) {
                String filename = productImage.getImagePath();
                filenames += filename + ",";
            }
            filenames = filenames.substring(0, filenames.length() - 1);


            GetProductReadRes getProductReadRes = GetProductReadRes.builder()
                    .idx(product.getIdx())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .salePrice(product.getSalePrice())
                    .productInfo(product.getProductInfo())
                    .filename(filenames)
                    .sellerIdx(product.getSellerIdx())
                    .peopleCount(product.getPeopleCount())
                    .startAt(product.getStartAt())
                    .closeAt(product.getCloseAt())
                    .build();

            return BaseResponse.successResponse("요청 성공", getProductReadRes);
        }
        return null;
    }

    @Transactional
    public void update(String email, PatchProductUpdateReq patchProductUpdateReq){

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new EcommerceApplicationException(
                ErrorCode.USER_NOT_FOUND, String.format("%s을 찾을 수 없습니다.", email), ErrorCode.USER_NOT_FOUND.getCode());

        Optional<Product> result = productRepository.findById(patchProductUpdateReq.getId());
        if (result.isPresent()) {
            Product existingProduct = productRepository.findById(patchProductUpdateReq.getId())
                    .orElseThrow(() -> new EcommerceApplicationException(
                            ErrorCode.PRODUCT_NOT_FOUND, String.format("%d번 상품은 존재하지 않습니다.", patchProductUpdateReq.getId()), ErrorCode.PRODUCT_NOT_FOUND.getCode()));
            // DTO에서 값이 주어진 속성만 변경
            if (patchProductUpdateReq.getProductName() != null) {
                existingProduct.setProductName(patchProductUpdateReq.getProductName());
            }
            if (patchProductUpdateReq.getPrice() != null) {
                existingProduct.setPrice(patchProductUpdateReq.getPrice());
            }
            if (patchProductUpdateReq.getSalePrice() != null) {
                existingProduct.setSalePrice(patchProductUpdateReq.getSalePrice());
            }
            if (patchProductUpdateReq.getProductInfo() != null) {
                existingProduct.setProductInfo(patchProductUpdateReq.getProductInfo());
            }
            if (patchProductUpdateReq.getProductImages() != null) {
                existingProduct.setImages(patchProductUpdateReq.getProductImages());
            }
            // 변경된 엔티티 저장
            productRepository.save(existingProduct);
        }
    }

    public void delete(String email, Long idx) {

        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new EcommerceApplicationException(
                ErrorCode.USER_NOT_FOUND, String.format("%s을 찾을 수 없습니다.", email), ErrorCode.USER_NOT_FOUND.getCode());

        Optional<Product> product =  productRepository.findById(idx);
        if(product.isPresent()) {
            Product product1 = product.get();
            Long productIdx = product1.getIdx();
            productRepository.deleteById(productIdx);
        }
    }
}