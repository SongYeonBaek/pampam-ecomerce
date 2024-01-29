package com.example.demo.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataSellerRepository extends JpaRepository<SellerJpaEntity, Long> {
    Optional<SellerJpaEntity> findByEmail(String email);
}
