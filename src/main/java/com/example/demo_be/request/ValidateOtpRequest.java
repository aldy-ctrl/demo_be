package com.example.demo_be.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateOtpRequest {

   private String username;
   private String otpCode;
}
