package com.example.demo_be.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.demo_be.base.controller.BaseController;
import com.example.demo_be.base.response.Response;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.response.UserResponse;
import com.example.demo_be.service.userService.UserService;
import com.example.demo_be.util.ResponseUtil;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

   @Autowired
   UserService userService;

   @Autowired
   private ResponseUtil responseUtil;

   @PostMapping("/create")
   public Response<UserResponse> create(Authentication authentication, @RequestBody @Valid UserRequest req) {

      UserResponse loginResponse = userService.createUser(req, this.getLoginUsername(authentication));

      return responseUtil.generateResponseSuccess(loginResponse);
   }

   @GetMapping("/get/{id}")
   public Response<UserResponse> getByUsername(Authentication authentication, @PathVariable @NotBlank String id) {

      UserResponse loginResponse = userService.getUser(id);

      return responseUtil.generateResponseSuccess(loginResponse);
   }

}
