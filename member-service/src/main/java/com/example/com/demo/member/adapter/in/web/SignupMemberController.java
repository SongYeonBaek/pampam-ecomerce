package com.example.com.demo.member.adapter.in.web;

import com.example.com.demo.member.application.port.in.SignupMemberCommand;
import com.example.com.demo.member.application.port.in.SignupMemberInport;
import com.example.com.demo.member.domain.Member;
import com.example.demo.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class SignupMemberController {
    private final SignupMemberInport signupMemberInport;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public Member signupMember(@RequestBody SignupMemberRequest request){
        SignupMemberCommand command = SignupMemberCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword()).build();

        return signupMemberInport.signupMember(command);
    }
}
