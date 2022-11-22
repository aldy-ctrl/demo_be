package com.example.demo_be.controller;

import java.util.Base64;

import javax.validation.Valid;

import com.example.demo_be.base.response.Response;
import com.example.demo_be.base.response.ResponseCustom;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.response.LoginResponse;
import com.example.demo_be.response.UserResponse;
import com.example.demo_be.service.userService.LoginService;
import com.example.demo_be.service.userService.UserService;
import com.example.demo_be.util.ResponseUtil;
import com.example.demo_be.vo.User;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

   @Autowired
   LoginService loginService;

   @Autowired
   UserService userService;

   @Autowired
   private ResponseUtil responseUtil;

   @PostMapping("/signIn")
   public Response<LoginResponse> login(@RequestBody @Valid User user) {

      String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());

      LoginResponse loginResponse = loginService.login(user.getUsername(), encodedPassword);

      return responseUtil.generateResponseSuccess(loginResponse);
   }

   @PostMapping("/signUp")
   public ResponseEntity<ResponseCustom<UserResponse>> create(@RequestBody UserRequest req) {

      return userService.signUp(req);
   }

   protected UserDetails getLoginUserInfo(Authentication authentication) {

      return (UserDetails) authentication.getPrincipal();
   }

   protected String getLoginUsername(Authentication authentication) {

      return this.getLoginUserInfo(authentication).getUsername();
   }

}
