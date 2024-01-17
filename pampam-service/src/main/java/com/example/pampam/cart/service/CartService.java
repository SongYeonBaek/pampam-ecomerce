package com.example.pampam.cart.service;

import com.example.pampam.cart.model.entity.Cart;
import com.example.pampam.cart.model.response.GetCartListRes;
import com.example.pampam.cart.model.response.PostCartInRes;
import com.example.pampam.cart.repository.CartRepository;
import com.example.pampam.common.BaseResponse;
import com.example.pampam.exception.EcommerceApplicationException;
import com.example.pampam.exception.ErrorCode;
import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.repository.ConsumerRepository;
import com.example.pampam.product.model.entity.Product;
import com.example.pampam.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ConsumerRepository consumerRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse<PostCartInRes> cartIn(Long productIdx, String email) {
        Optional<Consumer> consumer = consumerRepository.findByEmail(email);

        if (consumer.isPresent()) {
            Cart cart = cartRepository.save(Cart.builder()
                    .product(Product.builder().idx(productIdx).build())
                    .consumer(consumer.get())
                    .build());

            PostCartInRes product = PostCartInRes.builder()
                    .productId(cart.getProduct().getIdx())
                    .productName(cart.getProduct().getProductName())
                    .build();

            return BaseResponse.successResponse("요청 성공", product);
        } else {
            throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public BaseResponse<List<GetCartListRes>> cartList(String email) {

        Optional<Consumer> consumer = consumerRepository.findByEmail(email);

        if (consumer.isPresent()) {
            List<Cart> carts = cartRepository.findAllByConsumer(Consumer.builder().consumerIdx(consumer.get().getConsumerIdx()).build());
            List<GetCartListRes> cartList = new ArrayList<>();

            for (Cart cart : carts) {
                Product product = cart.getProduct();
                cartList.add(GetCartListRes.builder()
                        .idx(cart.getIdx())
                        .productName(product.getProductName())
                        .price(product.getPrice())
//                        .image(product.getImages().get(0).getImagePath())
                        .build());
            }
            return BaseResponse.successResponse("요청 성공", cartList);
        } else {
            throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public BaseResponse<String> updateCart(String token, Long cartIdx) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.split(" ")[1];
        }

        Long idx = JwtUtils.getUserIdx(token, secretKey);

        Optional<Consumer> consumer = consumerRepository.findById(idx);

        if (consumer.isPresent()) {
            cartRepository.deleteById(cartIdx);
            return BaseResponse.successResponse("요청 성공", consumer.get().getEmail());
        } else {
            throw new EcommerceApplicationException(ErrorCode.USER_NOT_FOUND);
        }
    }
}