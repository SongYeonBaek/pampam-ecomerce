package com.example.social.adapter.in;

import com.example.demo.common.WebAdapter;
import com.example.social.application.port.in.SocialLoginCommand;
import com.example.social.application.port.in.SocialLoginInport;
import com.example.social.application.port.in.SocialSignUpCommand;
import com.example.social.application.port.in.SocialSignUpInport;
import com.example.social.domain.JwtToken;
import com.example.social.domain.SocialLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequestMapping("/social")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class SocialLoginController {

    private final SocialSignUpInport socialSignUpInport;
    private final SocialLoginInport socialLoginInport;

    @RequestMapping("/signup")
    public SocialLogin socialSignUp(SocialSignUpReq socialLoginReq) {

        log.info("code {}", socialLoginReq.getCode());

        return socialSignUpInport.createSocialSignUp(SocialSignUpCommand.builder()
                .code(socialLoginReq.getCode())
                .build());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public JwtToken socialLogin(@RequestBody SocialLoginReq socialLoginReq) {
        return socialLoginInport.socialLogin(SocialLoginCommand.builder().email(socialLoginReq.getEmail()).build());
    }
}
