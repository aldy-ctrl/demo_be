package com.example.demo_be.service;

import com.example.demo_be.base.service.BaseService;
import com.example.demo_be.response.LoginResponse;

public interface LoginService extends BaseService {

   LoginResponse login(String username, String password);

}
