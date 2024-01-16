package com.example.email.adapter.out.persistence;

import com.example.email.application.out.CreateEmailCertOutport;
import com.example.email.application.out.VerifyEmailCertOutport;
import com.example.email.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailCertPersistenceAdapter implements VerifyEmailCertOutport, CreateEmailCertOutport {



    @Override
    public void verifyEmailCert(EmailCert emailCert) {

    }

    @Override
    public void createEmailCert(EmailCert emailCert) {

    }
}
