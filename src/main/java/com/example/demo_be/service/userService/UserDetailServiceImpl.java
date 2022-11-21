package com.example.demo_be.service.userService;

import java.util.ArrayList;
import java.util.List;

import com.example.demo_be.entity.UserEntity;
import com.example.demo_be.exception.ValidationException;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService, UserDetailsService {

   @Autowired
   UserRepository repoUser;

   @Override
   public UserDetails loadUserByUsername(String username) {

      UserEntity user = repoUser.findById(username).orElse(null);
      if (user == null) {
         throw new ValidationException("username null", username);
      }

      return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
   }

}
