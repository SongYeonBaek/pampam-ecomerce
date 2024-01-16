package com.example.email.adapter.out.persistence;

import com.example.email.application.out.VerifyEmailCertOutport;
import com.example.email.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailCertPersistenceAdapter implements VerifyEmailCertOutport {



    @Override
    public void verifyEmailCert(EmailCert emailCert) {

    }
}
