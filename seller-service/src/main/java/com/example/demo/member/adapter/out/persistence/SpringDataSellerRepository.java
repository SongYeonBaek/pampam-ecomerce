package com.example.demo.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSellerRepository extends JpaRepository<SellerJpaEntity, Long> {
}
