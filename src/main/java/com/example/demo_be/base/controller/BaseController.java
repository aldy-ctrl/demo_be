package com.example.demo_be.base.controller;

import com.example.demo_be.vo.UserInfo;

import org.springframework.security.core.Authentication;

public class BaseController {
    protected UserInfo getLoginUserInfo(Authentication authentication) {

        return (UserInfo) authentication.getPrincipal();
    }

    protected String getLoginUsername(Authentication authentication) {

        return this.getLoginUserInfo(authentication).getUsername();
    }

}
