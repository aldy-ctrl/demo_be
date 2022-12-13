package com.example.demo_be.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

   private String username;
   private String password;
   private String retypePassword;
   private String fullName;
   private String email;
   private String mobilePhone;
   private boolean isChangeEmail;

}
