package com.example.com.demo.member.adapter.in.web;

import com.example.com.demo.member.application.port.in.LoginMemberCommand;
import com.example.com.demo.member.application.port.in.LoginMemberInport;
import com.example.com.demo.member.domain.JwtToken;
import com.example.demo.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class LoginMemberController {

    private final LoginMemberInport memberInport;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public JwtToken login(@RequestBody LoginMemberRequest request) {
        return memberInport.login(LoginMemberCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build());
    }
}
