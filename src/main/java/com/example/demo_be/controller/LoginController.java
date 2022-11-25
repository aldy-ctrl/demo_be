package com.example.demo_be.controller;

import java.util.Base64;

import javax.validation.Valid;

import com.example.demo_be.base.response.Response;
import com.example.demo_be.base.response.ResponseCustom;
import com.example.demo_be.common.CommonMethod;
import com.example.demo_be.entity.UserEntity;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.request.ValidateOtpRequest;
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
   UserRepository userRepository;

   @Autowired
   private ResponseUtil responseUtil;

   // @PostMapping("/signIn")
   // public Response<LoginResponse> login(@RequestBody @Valid User user) {
   //    UserEntity cekIdUser = userRepository.findById(user.getUsername()).orElse(null);

   //    if (cekIdUser != null && cekIdUser.getDeletedFlag() == false) {
   //       return CommonMethod.badReq("Username sudah terpakai ");
   //    }

   //    String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());

   //    LoginResponse loginResponse = loginService.login(user.getUsername(), encodedPassword);

   //    return responseUtil.generateResponseSuccess(loginResponse);
   // }

   @PostMapping("/signIn")
   public ResponseEntity<ResponseCustom<LoginResponse>> login(@RequestBody @Valid User user) {
      String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());

      UserEntity cekIdUser = userRepository.findById(user.getUsername()).orElse(null);
      if (cekIdUser == null) {
         return CommonMethod.badReq("User tidak di temukan ");
      }

      if (!cekIdUser.getPassword().equals(encodedPassword)) {
         return CommonMethod.badReq("Password salah ");
      }

      if (cekIdUser.getFlagRegis() == false) {
         return CommonMethod.badReq("Username tersebut belum melakukan regist ");
      }

      LoginResponse loginResponse = loginService.login(user.getUsername(), encodedPassword);

      return CommonMethod.success(loginResponse);
   }

   @PostMapping("/signUp")
   public ResponseEntity<ResponseCustom<UserResponse>> create(@RequestBody UserRequest req) {

      return userService.signUp(req);
   }

   @PostMapping("/verifyOtpCode")
   public ResponseEntity<ResponseCustom<UserResponse>> verifyOtpCode(@RequestBody ValidateOtpRequest req) {

      return userService.validateOtpCode(req);
   }

   protected UserDetails getLoginUserInfo(Authentication authentication) {

      return (UserDetails) authentication.getPrincipal();
   }

   protected String getLoginUsername(Authentication authentication) {

      return this.getLoginUserInfo(authentication).getUsername();
   }

}
