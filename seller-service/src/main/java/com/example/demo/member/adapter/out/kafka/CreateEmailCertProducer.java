package com.example.demo.member.adapter.out.kafka;



import com.example.demo.common.ExternalSystemAdapter;
import com.example.demo.member.application.port.out.SignupSellerEventPort;
import com.example.demo.member.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class CreateEmailCertProducer implements SignupSellerEventPort {

    private final KafkaTemplate kafkaTemplate;


    @Override
    public void signupMemberEvent(Seller seller) {
        ProducerRecord<String, String> record =
                new ProducerRecord<>("test", ""+seller.getId(), seller.getEmail());
        kafkaTemplate.send(record);
    }
}