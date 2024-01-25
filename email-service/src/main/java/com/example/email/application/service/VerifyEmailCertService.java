package com.example.email.application.service;

import com.example.email.adapter.out.persistence.EmailCertEntity;
import com.example.email.application.in.VerifyEmailCertCommand;
import com.example.email.application.in.VerifyEmailCertInport;
import com.example.email.application.out.VerifyEmailCertOutport;
import com.example.email.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyEmailCertService implements VerifyEmailCertInport {


    private final VerifyEmailCertOutport emailCertOutport;

    @Override
    public EmailCertEntity verifyCertEmail(VerifyEmailCertCommand command) {
        return emailCertOutport.verifyEmailCert(EmailCert.builder()
                .email(command.getEmail())
                .uuid(command.getUuid())
                .build());
    }
}
