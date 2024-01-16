package com.example.email.adapter.in.kafka;


import com.example.email.application.in.CreateEmailCertCommand;
import com.example.email.application.in.CreateEmailCertInport;
import com.example.email.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateEmailCertConsumer {  // kafka에서 메세지를 받는 곳을 consumer라고 한다.

    private final CreateEmailCertInport emailCertInport;

    // 지정한 토픽을 통해서 메세지를 받는다.
    @KafkaListener(topics = "signup", groupId = "signup-group-00")
    public EmailCert modifyMember(ConsumerRecord<String, String> record) {
        String uuid = UUID.randomUUID().toString();
        return emailCertInport.createEmailCert(
                CreateEmailCertCommand.builder()
                .email(record.value())
                .uuid(uuid)
                .build());

    }
}
