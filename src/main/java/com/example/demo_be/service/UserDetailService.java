package com.example.demo_be.service;

import com.example.demo_be.vo.UserVo;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {

   UserDetails loadUserByUsername(String username);

   UserVo getPass(String username);

}
