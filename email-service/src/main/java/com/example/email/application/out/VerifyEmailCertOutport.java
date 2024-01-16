package com.example.email.application.out;

import com.example.email.domain.EmailCert;

public interface VerifyEmailCertOutport {
    void verifyEmailCert(EmailCert emailCert);
}
