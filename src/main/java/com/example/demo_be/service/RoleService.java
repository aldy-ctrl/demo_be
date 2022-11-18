package com.example.demo_be.service;

import com.example.demo_be.base.service.BaseService;
import com.example.demo_be.vo.AuthInfoVO;

public interface RoleService extends BaseService {
   AuthInfoVO getAuthorizationInfo(String username);

   Boolean isAdminFlag(String username);
}
