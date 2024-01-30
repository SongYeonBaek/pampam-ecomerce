package com.example.pampam.cart.repository;

import com.example.pampam.cart.model.entity.Cart;
import com.example.pampam.member.model.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByConsumerIdx(Long consumerIdx);
}
