package com.example.email.adapter.in.kafka;


import com.example.email.application.in.VerifyEmailCertCommand;
import com.example.email.application.in.VerifyEmailCertInport;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerifyEmailCertConsumer {

    private final VerifyEmailCertInport emailCertInport;

//    @KafkaListener(topics = "verifyEmail", groupId = "emailcert-group-00")
    public Boolean modifyMember(ConsumerRecord<String, String> record) {
        Boolean result = emailCertInport.verifyCertEmail(VerifyEmailCertCommand.builder()
                .email(record.value())
                .build());

        return result;
    }
}
