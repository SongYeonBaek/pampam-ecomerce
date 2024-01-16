package com.example.social.application.port.out;

import com.example.social.adapter.out.social.LoginUserInfo;

public interface SocialApiOutport {

    LoginUserInfo socialLoginApi(String accessToken);
}
