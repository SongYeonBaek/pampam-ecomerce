package com.example.email.application.out;

import com.example.email.adapter.out.persistence.EmailCertEntity;
import com.example.email.domain.EmailCert;

public interface CreateEmailCertOutport {
    EmailCertEntity createEmailCert(EmailCert emailCert);
}
