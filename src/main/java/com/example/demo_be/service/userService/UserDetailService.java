package com.example.demo_be.service.userService;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {

   UserDetails loadUserByUsername(String username);

}
