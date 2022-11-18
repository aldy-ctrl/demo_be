package com.example.demo_be.repository;

import java.util.List;

import com.example.demo_be.vo.RoleVo;

public interface RoleDAO {

   List<RoleVo> getActiveRoles(String username);

}
