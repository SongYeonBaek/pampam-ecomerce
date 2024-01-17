package com.example.email.application.in;

import com.example.email.domain.EmailCert;

public interface CreateEmailCertInport {
    EmailCert createEmailCert(CreateEmailCertCommand command);
}
