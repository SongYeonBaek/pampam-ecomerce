package com.example.pampam.member.controller;

import com.example.pampam.common.BaseResponse;
import com.example.pampam.member.model.request.*;
import com.example.pampam.member.service.EmailVerifyService;
import com.example.pampam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailVerifyService emailVerifyService;

    @RequestMapping(method = RequestMethod.POST, value = "/consumer/signup")
    public ResponseEntity consumerSignup(@RequestBody ConsumerSignupReq memberSignupReq){
//        memberService.consumerSignup(memberSignupReq);

        return ResponseEntity.ok().body(memberService.consumerSignup(memberSignupReq));
    }
    @RequestMapping(method = RequestMethod.POST, value = "/seller/signup")
    public ResponseEntity sellerSignup(@RequestPart SellerSignupReq sellerSignupReq, @RequestPart MultipartFile image){
//        memberService.sellerSignup(sellerSignupReq);

        return ResponseEntity.ok().body(memberService.sellerSignup(sellerSignupReq,image));
    }


    @RequestMapping(method = RequestMethod.POST, value = "/consumer/login")
    public ResponseEntity memberLogin(@RequestBody ConsumerLoginReq consumerLoginReq){


        return ResponseEntity.ok().body(memberService.consumerLogin(consumerLoginReq));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/seller/login")
    public ResponseEntity sellerLogin(@RequestBody SellerLoginReq sellerLoginReq){

        return ResponseEntity.ok().body(memberService.sellerLogin(sellerLoginReq));
    }
    @RequestMapping(method = RequestMethod.PATCH, value = "/consumer/update")
    public ResponseEntity consumerUpdate(@RequestBody ConsumerUpdateReq consumerUpdateReq){

        return ResponseEntity.ok().body(memberService.consumerUpdate(consumerUpdateReq));
    }
    @RequestMapping(method = RequestMethod.PATCH, value = "/seller/update")
    public ResponseEntity sellerUpdate(@RequestBody SellerUpdateReq sellerUpdateReq){

        return ResponseEntity.ok().body(memberService.sellerUpdate(sellerUpdateReq));
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/consumer/delete")
    public ResponseEntity<BaseResponse<String>> consumerDelete(@RequestBody ConsumerDeleteReq consumerDeleteReq){

        return ResponseEntity.ok().body(memberService.consumerDelete(consumerDeleteReq));
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/seller/delete")
    public ResponseEntity sellerDelete(@RequestBody SellerDeleteReq sellerDeleteReq){

        return ResponseEntity.ok().body(memberService.sellerDelete(sellerDeleteReq));
    }

    @RequestMapping(method = RequestMethod.GET,value = "confirm")
    public RedirectView confirm(GetEmailConfirmReq getEmailConfirmReq){

        return emailVerifyService.verify(getEmailConfirmReq);



    }
}
