package com.icedevcloud.auth.controller;

import com.icedevcloud.common.core.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenStore tokenStore;

    @DeleteMapping("logOut")
    public R<Boolean> logOut(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, "").trim();
        return R.ok(true);
    }

}
