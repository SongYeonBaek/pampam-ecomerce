//package com.example.email.adapter.in.web;
//
//import com.example.email.application.in.VerifyEmailCertCommand;
//import com.example.email.application.in.VerifyEmailCertInport;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/emailcert")
//@RequiredArgsConstructor
//public class VerifyEmailCertController {
//
//    private final VerifyEmailCertInport emailCertInport;
//
//    @RequestMapping(method = RequestMethod.GET, value = "/verify")
//    public Boolean verify(String email, String uuid) {
//
//        System.out.println(email);
//        System.out.println(uuid);
//
//        return emailCertInport.verifyCertEmail(VerifyEmailCertCommand.builder()
//                .email(email)
//                .uuid(uuid)
//                .build());
//    }
//}
