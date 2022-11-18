package com.example.demo_be.vo;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class AuthInfoVO {

   @NonNull
   private List<String> roleCds;

   @NonNull
   private Boolean isAdminFlag;

}
