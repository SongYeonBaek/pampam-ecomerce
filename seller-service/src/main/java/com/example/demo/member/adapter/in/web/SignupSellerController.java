package com.example.demo.member.adapter.in.web;


import com.example.demo.common.WebAdapter;
import com.example.demo.member.application.port.in.SignupSellerCommand;
import com.example.demo.member.application.port.in.SignupSellerInport;
import com.example.demo.member.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@WebAdapter
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class SignupSellerController {
    private final SignupSellerInport signupSellerInport;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public Seller signupSeller(@RequestPart SignupSellerRequest request,@RequestPart MultipartFile file){
        SignupSellerCommand command = SignupSellerCommand.builder()
                .email(request.getEmail())
                .sellerPW(request.getSellerPW())
                .sellerName(request.getSellerName())
                .sellerAddr(request.getSellerAddr())
                .sellerPhoneNum(request.getSellerPhoneNum())
                .sellerBusinessNumber(request.getSellerBusinessNumber())
                .file(file)
                .build();

        return signupSellerInport.signupSeller(command);
    }
}
