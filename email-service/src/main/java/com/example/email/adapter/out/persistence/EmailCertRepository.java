package com.example.email.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCertRepository extends JpaRepository<EmailCertEntity, Long> {
    Optional<EmailCertEntity> findByEmail(String email);
}
