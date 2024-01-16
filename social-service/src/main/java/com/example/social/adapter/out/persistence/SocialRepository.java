package com.example.social.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<SocialLoginJpaEntity, Long> {
    Optional<SocialLoginJpaEntity> findByEmail(String email);
}
