package com.example.demo_be.service.impl;

import com.example.demo_be.base.service.impl.BaseServiceImpl;
import com.example.demo_be.exception.ValidationException;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.response.LoginResponse;
import com.example.demo_be.service.LoginService;
import com.example.demo_be.util.JwtUtil;
import com.example.demo_be.vo.JwtInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
   @Autowired
   private JwtUtil jwtUtil;

   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   UserRepository userRepository;

   @Autowired
   UserDetailServiceImpl userService;

   @Override
   public LoginResponse login(String username, String password) {

      String COMMNERR00005 = "Forbidden";

      try {
         Authentication auth = authenticationManager
               .authenticate(new UsernamePasswordAuthenticationToken(username, password));
      } catch (BadCredentialsException ex) {
         throw new ValidationException(COMMNERR00005);
      }

      return this.generateLoginResponse(username);
   }

   private LoginResponse generateLoginResponse(String username) {

      JwtInfo accessTokenInfo = jwtUtil.generateAccessToken(username);

      long expMiliDetik = accessTokenInfo.getAge();
      long expDetik = expMiliDetik / 1000;
      long expMenit = expDetik / 60;
      long menitAktual = expMenit % 60;
      String finalAge = Long.toString(menitAktual);

      LoginResponse response = new LoginResponse();
      response.setTokenType(jwtUtil.getTokenType());
      response.setAccessToken(accessTokenInfo.getToken());
      response.setAccessTokenExpDate(accessTokenInfo.getExpiration());
      response.setAccessTokenAge(finalAge + " Minutes");
      return response;
   }

}
