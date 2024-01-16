package com.example.demo.member.application.port.in;


import com.example.demo.member.domain.Seller;

public interface SignupSellerInport {
    Seller signupSeller(SignupSellerCommand command);
}
