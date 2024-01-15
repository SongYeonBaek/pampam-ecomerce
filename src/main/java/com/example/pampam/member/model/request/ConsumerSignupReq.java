package com.example.pampam.member.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Builder
@Data
public class ConsumerSignupReq {
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String consumerPW;
    private String consumerName;
    private String consumerAddr;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호를 입력해주세요")
    private String consumerPhoneNum;


}
