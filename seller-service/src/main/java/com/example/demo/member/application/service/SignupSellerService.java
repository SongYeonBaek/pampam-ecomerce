package com.example.demo.member.application.service;


import com.example.demo.member.adapter.out.persistence.SellerJpaEntity;
import com.example.demo.member.application.port.in.SignupSellerCommand;
import com.example.demo.member.application.port.in.SignupSellerInport;
//import com.example.demo.member.application.port.out.SignupSellerEventPort;
import com.example.demo.member.application.port.out.SignupSellerOutport;
import com.example.demo.member.application.port.out.UploadSellerImagePort;
import com.example.demo.member.common.BaseResponse;
import com.example.demo.member.domain.Seller;
import com.example.demo.member.domain.SellerImage;
import com.example.demo.member.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupSellerService implements SignupSellerInport {
    private final SignupSellerOutport signupSellerOutport;
//    private final SignupSellerEventPort signupSellerEventPort;
    private final UploadSellerImagePort uploadSellerImagePort;
    @Override
    public BaseResponse<Object> signupSeller(SignupSellerCommand command) {
        Seller sellerInfo = Seller.builder()
                .email(command.getEmail())
                .sellerPW(command.getSellerPW())
                .sellerName(command.getSellerName())
                .sellerAddr(command.getSellerAddr())
                .sellerPhoneNum(command.getSellerPhoneNum())
                .sellerBusinessNumber(command.getSellerBusinessNumber())
                .build();

        SellerJpaEntity seller = signupSellerOutport.signupSeller(sellerInfo);

        if (seller != null) {
//            signupSellerEventPort.signupSellerEvent(Seller.builder()
//                    .id(seller.getId())
//                    .email(seller.getEmail())
//                    .build());

            uploadSellerImagePort.uploadSellerImagePort(SellerImage.builder()
                    .email(seller.getEmail())
                    .file(command.getFile())
                    .build());
            Seller result = Seller.builder()
                    .id(seller.getId())
                    .email(seller.getEmail())
                    .build();

            return BaseResponse.successResponse("회원가입이 정상적으로 처리되었습니다.", result);
        } else {
            return BaseResponse.failResponse(ErrorCode.USER_NOT_FOUND.getCode(), "존재하지 않는 회원입니다.");
        }
    }
}

