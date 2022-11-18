package com.example.demo_be.vo;

import java.util.List;

import com.example.demo_be.request.RoleMenuReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMenuUserVo {

   private String roleCd;
   private String roleName;
   private Boolean adminFlag;
   private String username;
   private List<RoleMenuReq> listRoleMenu;

}
