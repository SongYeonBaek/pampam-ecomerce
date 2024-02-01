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
import com.example.pampam.product.model.response.PostProductRegisterRes;
import com.example.pampam.product.repository.ProductRepository;
import com.example.pampam.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse<PostProductRegisterRes> register(String token, PostProductRegisterReq productRegisterReq, MultipartFile[] images) {

        token = JwtUtils.replaceToken(token);
        Claims sellerInfo = JwtUtils.getSellerInfo(token, secretKey);

        if (sellerInfo.get("authority", String.class).equals("SELLER")) {
            Product product = productRepository.save(Product.dtoToEntity(productRegisterReq, sellerInfo));
            for (MultipartFile uploadFile : images) {
                String uploadPath = imageSaveService.uploadFile(uploadFile);
                imageSaveService.saveFile(product.getIdx(), uploadPath);
            }

            PostProductRegisterRes postProductRegisterRes = PostProductRegisterRes.entityToDto(product);
            return BaseResponse.successResponse("요청 성공", postProductRegisterRes);

        } else {
            return BaseResponse.failResponse(7000, "요청 실패");
        }
    }

    // TODO: 상품 전체 조회
    public BaseResponse<Object> list(String token, Integer page, Integer size) {
        token = JwtUtils.replaceToken(token);
        String email = JwtUtils.getUsername(token, secretKey);
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


            GetProductReadRes getProductReadRes = GetProductReadRes.entityToDto(product, filenames);
            productReadResList.add(getProductReadRes);
        }
        // DtoToRes
        return BaseResponse.successResponse("요청 성공", productReadResList);
    }

    public BaseResponse<GetProductReadRes> read(String token, Long idx) {
        token = JwtUtils.replaceToken(token);
        String email = JwtUtils.getUsername(token, secretKey);
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
            GetProductReadRes getProductReadRes = GetProductReadRes.entityToDto(product, filenames);

            return BaseResponse.successResponse("요청 성공", getProductReadRes);
        }
        return null;
    }

    @Transactional
    public BaseResponse<Long> update(String token, PatchProductUpdateReq patchProductUpdateReq){
        token = JwtUtils.replaceToken(token);
        String email = JwtUtils.getUsername(token, secretKey);
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
            Product updateProduct = productRepository.save(existingProduct);
            return BaseResponse.successResponse("수정 성공", updateProduct.getIdx());
        } else {
            throw new EcommerceApplicationException(
                    ErrorCode.PRODUCT_NOT_FOUND,
                    String.format("%s를 찾을 수 없습니다.", result.get().getProductName()),
                    ErrorCode.PRODUCT_NOT_FOUND.getCode());
        }
    }

    public BaseResponse<Long> delete(String token, Long idx) {
        token = JwtUtils.replaceToken(token);
        String email = JwtUtils.getUsername(token, secretKey);
        Optional<Seller> seller = sellerRepository.findByEmail(email);

        if(seller.isPresent()) {
            System.out.println("인증된 접근입니다.");
        } else throw new EcommerceApplicationException(
                ErrorCode.USER_NOT_FOUND, String.format("%s을 찾을 수 없습니다.", email), ErrorCode.USER_NOT_FOUND.getCode());

        Optional<Product> product =  productRepository.findById(idx);
        if(product.isPresent()) {
            Product productInfo = product.get();
            Long productIdx = productInfo.getIdx();
            productRepository.deleteById(productIdx);

            return BaseResponse.successResponse("상품 삭제 성공", productIdx);
        } else {
            throw new EcommerceApplicationException(
                    ErrorCode.PRODUCT_NOT_FOUND,
                    String.format("%s를 찾을 수 없습니다.", product.get().getProductName()),
                    ErrorCode.PRODUCT_NOT_FOUND.getCode());
        }
    }
}