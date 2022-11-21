package com.example.demo_be.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.example.demo_be.entity.UserEntity;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.vo.AuthInfoVO;
import com.example.demo_be.vo.JwtInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtUtil {

   public static final String CLAIM_KEY_AUTHORITIES = "authorities";
   @Value("${jwt.secret:#{null}}")
   private String jwtSecret;
   @Value("${jwt.expired:#{null}}")
   private Integer jwtExpired;
   @Value("${jwt.tokenType:#{null}}")
   private String jwtTokenType;

   @Autowired
   ObjectMapper objectMapper;

   @Autowired
   UserRepository repo;

   public JwtInfo generateAccessToken(String username, String password) {

      UserEntity getUser = repo.findById(username).orElse(null);
      ArrayList<String> userAuth = new ArrayList<>();

      if (getUser == null) {
         userAuth.add(password);
      } else {
         userAuth.add(getUser.getPassword());
      }

      AuthInfoVO authorizationInfo = new AuthInfoVO();
      authorizationInfo.setPassword(userAuth);
      Date currentDt = new Date();
      Date expireToken = new Date(currentDt.getTime() + (jwtExpired));
      String token = Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
            // .claim(CLAIM_KEY_AUTHORITIES)
            .claim(CLAIM_KEY_AUTHORITIES, authorizationInfo)
            .setIssuedAt(currentDt).setExpiration(expireToken).signWith(SignatureAlgorithm.HS512,
                  jwtSecret.getBytes())
            .compact();

      return new JwtInfo(token, username, currentDt, expireToken, jwtExpired);
   }

   public Claims parseAccessToken(String token) {

      return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
   }

   public String getAccessToken(String authorizationString) {
      if (authorizationString != null && authorizationString.startsWith(jwtTokenType)) {
         return authorizationString.replaceFirst(jwtTokenType + " ", org.apache.commons.lang3.StringUtils.EMPTY);
      }

      return null;
   }

   public String getAccessToken(HttpServletRequest request) {
      String authenticationString = request.getHeader("Authorization");

      return this.getAccessToken(authenticationString);
   }

   public String getTokenType() {
      return jwtTokenType;
   }

   public AuthInfoVO getAuthorizationInfo(Claims claims) {
      Map map = (Map) claims.get(JwtUtil.CLAIM_KEY_AUTHORITIES);

      return objectMapper.convertValue(map, AuthInfoVO.class);
   }

}
