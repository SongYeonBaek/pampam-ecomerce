package com.example.pampam.member.repository;

import com.example.pampam.member.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByEmail(String email);

    Optional<Seller> findBySellerIdx(Long sellerIdx);
}
