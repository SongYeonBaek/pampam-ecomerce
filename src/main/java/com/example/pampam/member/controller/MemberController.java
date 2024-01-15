package com.example.pampam.member.controller;

import com.example.pampam.member.model.entity.Consumer;
import com.example.pampam.member.model.request.*;
import com.example.pampam.member.model.response.SuccessConsumerSignupRes;
import com.example.pampam.member.service.EmailVerifyService;
import com.example.pampam.member.service.KakaoService;
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
    private final KakaoService kakaoService;
    @RequestMapping(method = RequestMethod.GET, value = "/kakao")
    // 인가 코드 받아오는 코드
    public ResponseEntity kakao(String code) {
        System.out.println(code);
        /////////////////////////////////
        // 인가 코드로 토큰 받아오는 코드
        String accessToken = kakaoService.getKakaoToken(code);
        ///////////////////////////////////////////////////////////////
        // 토큰으로 사용자 정보 받아오는 코드
        KakaoEmailReq kakaoEmailReq = kakaoService.getUserInfo(accessToken);
        //////////////////////////////////////////////

        // 가져온 사용자 정보로 DB 확인
        Consumer consumer = memberService.getMemberByConsumerID(kakaoEmailReq.getEmail());

        if(consumer == null) {
            // DB에 없으면 회원 가입
            consumer = memberService.kakaoSignup(kakaoEmailReq);
        }
        // 로그인 처리(JWT 토큰 발급)
        return ResponseEntity.ok().body(kakaoService.kakaoLogin(consumer));
    }
    @RequestMapping(method = RequestMethod.POST, value = "/consumer/signup")
    public ResponseEntity consumerSignup(@RequestBody ConsumerSignupReq memberSignupReq){
//        memberService.consumerSignup(memberSignupReq);

        return ResponseEntity.ok().body(memberService.consumerSignup(memberSignupReq));
    }
    @RequestMapping(method = RequestMethod.POST, value = "/seller/signup")
    public ResponseEntity sellerSignup(@RequestPart SellerSignupReq sellerSignupReq,@RequestPart MultipartFile image){
//        memberService.sellerSignup(sellerSignupReq);

        return ResponseEntity.ok().body(memberService.sellerSignup(sellerSignupReq, image));
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
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(@RequestBody MemberDeleteReq memberDeleteReq){

        return ResponseEntity.ok().body(memberService.delete(memberDeleteReq));
    }

    @RequestMapping(method = RequestMethod.GET,value = "confirm")
    public RedirectView confirm(GetEmailConfirmReq getEmailConfirmReq){

        return emailVerifyService.verify(getEmailConfirmReq);



    }
}
