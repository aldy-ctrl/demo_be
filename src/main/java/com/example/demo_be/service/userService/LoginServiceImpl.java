package com.example.demo_be.service.userService;

import com.example.demo_be.base.service.impl.BaseServiceImpl;
import com.example.demo_be.exception.BusinessException;
import com.example.demo_be.exception.ValidationException;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.response.LoginResponse;
import com.example.demo_be.util.JwtUtil;
import com.example.demo_be.vo.JwtInfo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

      try {
         Authentication auth = authenticationManager
               .authenticate(new UsernamePasswordAuthenticationToken(username, password));
      } catch (BadCredentialsException ex) {
         throw new BusinessException("COMMNERR00008");
      }

      return this.generateLoginResponse(username, password);
   }

   private LoginResponse generateLoginResponse(String username, String password) {

      JwtInfo accessTokenInfo = jwtUtil.generateAccessToken(username, password);

      long expMiliDetik = accessTokenInfo.getAge();
      long hoursAktual = (expMiliDetik / (1000 * 60 * 60));
      String finalAge = Long.toString(hoursAktual);

      LoginResponse response = new LoginResponse();
      response.setTokenType(jwtUtil.getTokenType());
      response.setAccessToken(accessTokenInfo.getToken());
      response.setAccessTokenExpDate(accessTokenInfo.getExpiration());
      response.setAccessTokenAge(finalAge + " Hours");
      return response;
   }

}
