package com.example.demo.member.adapter.in.web;


import com.example.demo.common.WebAdapter;
import com.example.demo.member.application.port.in.SignupSellerCommand;
import com.example.demo.member.application.port.in.SignupSellerInport;
import com.example.demo.member.common.BaseResponse;
import com.example.demo.member.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@WebAdapter
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SignupSellerController {
    private final SignupSellerInport signupSellerInport;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public BaseResponse<Object> signupSeller(@RequestPart SignupSellerRequest request, @RequestPart(required = false) MultipartFile file){
        return signupSellerInport.signupSeller(SignupSellerCommand.buildCommand(request, file));
    }
}
