package com.example.email.adapter.in.kafka;


import com.example.email.application.in.CreateEmailCertCommand;
import com.example.email.application.in.CreateEmailCertInport;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateEmailCertConsumer {  // kafka에서 메세지를 받는 곳을 consumer라고 한다.

    private final CreateEmailCertInport emailCertInport;

    // 지정한 토픽을 통해서 메세지를 받는다.
    @KafkaListener(topics = "signup", groupId = "signup-group-00")
    public void modifyMember(ConsumerRecord<String, String> record) {
        System.out.println(record.toString());

        String uuid = UUID.randomUUID().toString();

        emailCertInport.createEmailCert(CreateEmailCertCommand.builder()
                        .email(record.value())
                        .uuid(uuid)
                        .build());

    }
}
