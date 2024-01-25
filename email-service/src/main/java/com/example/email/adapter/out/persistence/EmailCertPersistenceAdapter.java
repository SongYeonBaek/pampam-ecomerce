package com.example.email.adapter.out.persistence;

import com.example.email.application.out.CreateEmailCertOutport;
import com.example.email.application.out.VerifyEmailCertOutport;
import com.example.email.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailCertPersistenceAdapter implements VerifyEmailCertOutport, CreateEmailCertOutport {

    private final EmailCertRepository emailCertRepository;

    @Override
    public EmailCertEntity createEmailCert(EmailCert emailCert) {
        return emailCertRepository.save(EmailCertEntity.builder()
                .email(emailCert.getEmail())
                .uuid(emailCert.getUuid())
                        .status(false)
                .build());
    }


    @Override
    public EmailCertEntity verifyEmailCert(EmailCert emailCert) {
        Optional<EmailCertEntity> user = emailCertRepository.findByEmail(emailCert.getEmail());

        if (user.isPresent()) {
            if (user.get().getUuid().equals(emailCert.getUuid())) {
                return emailCertRepository.save(EmailCertEntity.builder()
                        .idx(user.get().getIdx())
                        .email(user.get().getEmail())
                                .uuid(user.get().getUuid())
                        .status(true).build());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
