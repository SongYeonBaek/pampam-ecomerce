package com.example.email.application.service;

import com.example.email.adapter.out.persistence.EmailCertEntity;
import com.example.email.application.in.CreateEmailCertCommand;
import com.example.email.application.in.CreateEmailCertInport;
import com.example.email.application.out.CreateEmailCertOutport;
import com.example.email.application.out.SendEmailOutport;
import com.example.email.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEmailCertService implements CreateEmailCertInport {

    private final CreateEmailCertOutport emailCertOutport;
    private final SendEmailOutport sendEmailOutport;

    @Override
    public EmailCert createEmailCert(CreateEmailCertCommand command) {
        EmailCert emailCert = EmailCert.builder()
                .email(command.getEmail())
                .uuid(command.getUuid())
                .build();
        EmailCertEntity emailInfo = emailCertOutport.createEmailCert(emailCert);
        sendEmailOutport.sendEmail(emailCert);

        return EmailCert.builder()
                .idx(emailInfo.getIdx())
                .email(emailInfo.getEmail())
                .uuid(emailInfo.getUuid())
                .status(emailCert.getStatus())
                .build();

    }
}
