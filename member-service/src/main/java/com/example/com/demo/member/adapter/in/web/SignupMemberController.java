package com.example.com.demo.member.adapter.in.web;

import com.example.com.demo.member.application.port.in.SignupMemberCommand;
import com.example.com.demo.member.application.port.in.SignupMemberInport;
import com.example.com.demo.member.common.BaseResponse;
import com.example.com.demo.member.domain.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin("*")
@Component
public class SignupMemberController {
    private final SignupMemberInport signupMemberInport;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public BaseResponse<Member> signupMember(@RequestBody SignupMemberRequest request){
        return signupMemberInport.signupMember(SignupMemberCommand.buildCommand(request));
    }
}
