//package com.example.demo.member.adapter.out.kafka;
//
//
//
//
//import com.example.demo.member.application.port.out.SignupSellerEventPort;
//import com.example.demo.member.domain.Seller;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CreateEmailCertProducer implements SignupSellerEventPort {
//
//
//
//
//    @Override
//    public void signupSellerEvent(Seller seller) {
//        ProducerRecord<String, String> record =
//                new ProducerRecord<>("signup", seller.getEmail());
//        kafkaTemplate.send(record);
//    }
//}