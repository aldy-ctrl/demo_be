package com.example.demo_be.service.userService;

import org.springframework.http.ResponseEntity;

import com.example.demo_be.base.response.ResponseCustom;
import com.example.demo_be.base.service.BaseService;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.request.ValidateOtpRequest;
import com.example.demo_be.response.UserResponse;

public interface UserService extends BaseService {

   UserResponse createUser(UserRequest req, String username);

   ResponseEntity<ResponseCustom<UserResponse>> signUp(UserRequest req);

   ResponseEntity<ResponseCustom<UserResponse>> validateOtpCode(ValidateOtpRequest request);

   ResponseEntity<ResponseCustom<UserResponse>> updateUser(UserRequest req, String username);

   UserResponse deletUser(String userId, String username);

   ResponseEntity<ResponseCustom<UserResponse>> getUser(String username);

   UserResponse searchUser(String username);
}
