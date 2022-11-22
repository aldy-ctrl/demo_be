package com.example.demo_be.response;

import com.example.demo_be.entity.UserEntity;

import lombok.Data;

@Data
public class UserResponse extends UserEntity {

    private String username;
   private String password;
   private String fullName;
   private String email;
   private String mobilePhone;
   
}
