package com.example.email.application.out;

import com.example.email.domain.EmailCert;

public interface SendEmailOutport {
    void sendEmail(EmailCert emailCert);
}
