package com.example.demo.member.adapter.in.web;


import com.example.demo.member.application.port.in.LoginSellerCommand;
import com.example.demo.member.application.port.in.LoginSellerInport;
import com.example.demo.member.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
@CrossOrigin("*")
public class LoginSellerController {

    private final LoginSellerInport loginSellerInport;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public BaseResponse<Object> login(@RequestBody LoginSellerRequest request) {
        return loginSellerInport.login(LoginSellerCommand.buildCommand(request));
    }
}
