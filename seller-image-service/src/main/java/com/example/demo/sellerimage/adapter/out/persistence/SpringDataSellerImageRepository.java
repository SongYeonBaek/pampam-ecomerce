package com.example.demo.sellerimage.adapter.out.persistence;

import com.example.demo.sellerimage.domain.SellerImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSellerImageRepository extends JpaRepository<SellerImageJpaEntity, Long> {
}
