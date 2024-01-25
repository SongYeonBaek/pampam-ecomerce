package com.example.email.application.in;

import com.example.email.adapter.out.persistence.EmailCertEntity;

public interface VerifyEmailCertInport {
    EmailCertEntity verifyCertEmail(VerifyEmailCertCommand command);
}
