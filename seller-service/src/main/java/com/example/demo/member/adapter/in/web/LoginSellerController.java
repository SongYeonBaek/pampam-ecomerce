package com.example.demo.member.adapter.in.web;

import com.example.demo.common.WebAdapter;
import com.example.demo.member.application.port.in.LoginSellerCommand;
import com.example.demo.member.application.port.in.LoginSellerInport;
import com.example.demo.member.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class LoginSellerController {

    private final LoginSellerInport loginSellerInport;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public JwtToken login(@RequestBody LoginSellerRequest request) {
        return loginSellerInport.login(LoginSellerCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build());
    }
}
