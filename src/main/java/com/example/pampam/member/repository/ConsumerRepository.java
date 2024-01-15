package com.example.pampam.member.repository;

import com.example.pampam.member.model.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    Optional<Consumer> findByEmail(String email);
}
