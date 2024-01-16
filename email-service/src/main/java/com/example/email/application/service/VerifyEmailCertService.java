package com.example.email.application.service;

import com.example.email.application.in.VerifyEmailCertCommand;
import com.example.email.application.in.VerifyEmailCertInport;
import com.example.email.application.out.VerifyEmailCertOutport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyEmailCertService implements VerifyEmailCertInport {


    private final VerifyEmailCertOutport emailCertOutport;

    @Override
    public Boolean verifyCertEmail(VerifyEmailCertCommand command) {



        return null;
    }
}
