package com.example.demo.sellerimage.adapter.out.kafka;

import com.example.demo.common.ExternalSystemAdapter;

import com.example.demo.sellerimage.application.port.out.CreateEmailCertEventPort;
import com.example.demo.sellerimage.domain.SellerImage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class CreateEmailCertProducer implements CreateEmailCertEventPort {
    private final KafkaTemplate kafkaTemplate;

    @Override
    public void createEmailCertEvent(SellerImage sellerImage) {
        ProducerRecord<String, String> record =
                new ProducerRecord<>("signup", ""+sellerImage.getId(), sellerImage.getEmail());

        kafkaTemplate.send(record);
    }
}
