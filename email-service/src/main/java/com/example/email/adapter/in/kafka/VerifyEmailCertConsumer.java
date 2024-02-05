package com.example.email.adapter.in.kafka;


import com.example.email.adapter.out.persistence.EmailCertEntity;
import com.example.email.application.in.VerifyEmailCertCommand;
import com.example.email.application.in.VerifyEmailCertInport;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Primary
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class VerifyEmailCertConsumer {

    private final VerifyEmailCertInport emailCertInport;

    @RequestMapping(method = RequestMethod.GET, value = "/email/verify")
    public RedirectView modifyMember(VerifyEmailCertRequest verifyEmailCertRequest) {
        EmailCertEntity result = emailCertInport.verifyCertEmail(
                VerifyEmailCertCommand.builder()
                        .email(verifyEmailCertRequest.getEmail())
                        .uuid(verifyEmailCertRequest.getUuid())
                        .build());

        if(result!=null){
            return new RedirectView("http://www.localfoodpam.kro.kr");
        }else{
            return new RedirectView("http://www.localfoodpam.kro.kr/");
        }
    }
}
