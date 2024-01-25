package com.example.email.adapter.in.kafka;


import com.example.email.adapter.out.persistence.EmailCertEntity;
import com.example.email.application.in.VerifyEmailCertCommand;
import com.example.email.application.in.VerifyEmailCertInport;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
@RequiredArgsConstructor
public class VerifyEmailCertConsumer {

    private final VerifyEmailCertInport emailCertInport;

    @RequestMapping(method = RequestMethod.GET, value = "/email/verify")
    public EmailCertEntity modifyMember(VerifyEmailCertRequest verifyEmailCertRequest) {
        EmailCertEntity result = emailCertInport.verifyCertEmail(
                VerifyEmailCertCommand.builder()
                .email(verifyEmailCertRequest.getEmail())
                        .uuid(verifyEmailCertRequest.getUuid())
                .build());

        return result;
    }
}
