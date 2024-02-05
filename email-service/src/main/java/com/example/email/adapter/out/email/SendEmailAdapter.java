package com.example.email.adapter.out.email;

import com.example.email.application.out.SendEmailOutport;
import com.example.email.domain.EmailCert;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailAdapter implements SendEmailOutport {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(EmailCert emailCert) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(emailCert.getEmail());
        mailMessage.setSubject("[팜팜] 이메일 인증 절차를 완료해주세요.");
        mailMessage.setText("http://112.171.51.134:7012/email/verify?email="
                + emailCert.getEmail() + "&uuid=" + emailCert.getUuid());
        emailSender.send(mailMessage);
    }
}
