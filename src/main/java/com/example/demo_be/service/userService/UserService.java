package com.example.demo_be.service.userService;

import com.example.demo_be.base.service.BaseService;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.response.UserResponse;

public interface UserService extends BaseService {

   UserResponse createUser(UserRequest req, String username);

   UserResponse updateUser(UserRequest req, String username);

   UserResponse deletUser(String userId, String username);

   UserResponse getUser(String username);

   UserResponse searchUser(String username);
}
