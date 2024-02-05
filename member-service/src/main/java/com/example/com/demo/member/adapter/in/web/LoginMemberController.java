package com.example.com.demo.member.adapter.in.web;

import com.example.com.demo.member.application.port.in.LoginMemberCommand;
import com.example.com.demo.member.application.port.in.LoginMemberInport;
import com.example.com.demo.member.common.BaseResponse;
import com.example.com.demo.member.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin("*")
@Component
public class LoginMemberController {

    private final LoginMemberInport memberInport;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public BaseResponse<String> login(@RequestBody LoginMemberRequest request) {
        return memberInport.login(LoginMemberCommand.buildCommand(request));
    }
}
