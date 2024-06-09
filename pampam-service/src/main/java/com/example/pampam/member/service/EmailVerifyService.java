package com.example.pampam.member.service;


import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.model.entity.EmailVerify;
import com.example.pampam.member.model.entity.Seller;
import com.example.pampam.member.model.request.GetEmailConfirmReq;
import com.example.pampam.member.model.request.SendEmailReq;
import com.example.pampam.member.repository.ConsumerRepository;
import com.example.pampam.member.repository.EmailVerifyRepository;
import com.example.pampam.member.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailVerifyRepository emailVerifyRepository;
    private final ConsumerRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final JavaMailSender emailSender;

    public void sendEmail(SendEmailReq sendEmailReq) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendEmailReq.getEmail());
        message.setSubject("로컬푸드 pampam 이메일 인증");
        String uuid = UUID.randomUUID().toString();
        message.setText("http://localhost:8080/member/confirm?email="
                + sendEmailReq.getEmail()
                + "&authority=" + sendEmailReq.getAuthority()
                + "&token=" + uuid
                + "&jwt=" + sendEmailReq.getAccessToken()
        );
        emailSender.send(message);
        create(sendEmailReq.getEmail(), uuid);
    }

    public RedirectView verify(GetEmailConfirmReq getEmailConfirmReq) {
        Optional<EmailVerify> result = emailVerifyRepository.findByEmail(getEmailConfirmReq.getEmail());
        if(result.isPresent()){
            EmailVerify emailVerify = result.get();
            if(emailVerify.getUuid().equals(getEmailConfirmReq.getToken())) {
                update(getEmailConfirmReq.getEmail(), getEmailConfirmReq.getAuthority());
                return new RedirectView("http://localhost:3000/emailconfirm/" + getEmailConfirmReq.getJwt());
            }
        }
        return new RedirectView("http://localhost:3000/emailCertError");
    }

    private void update(String email, String authority) {
        if (authority.equals("CONSUMER")){
            Optional<Consumer> result = memberRepository.findByEmail(email);
            if(result.isPresent()) {
                Consumer member = result.get();
                member.setStatus(true);
                memberRepository.save(member);
            }
        }else if (authority.equals("SELLER")){
            Optional<Seller> result = sellerRepository.findByEmail(email);
            if(result.isPresent()) {
                Seller seller = result.get();
                seller.setStatus(true);
                sellerRepository.save(seller);
            }
        }
    }
    private void create(String email, String uuid) {
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .uuid(uuid)
                .build();
        emailVerifyRepository.save(emailVerify);
    }
}
