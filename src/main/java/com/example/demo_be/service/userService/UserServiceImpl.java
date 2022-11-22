package com.example.demo_be.service.userService;

import java.util.Base64;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo_be.base.response.Response;
import com.example.demo_be.base.response.ResponseCustom;
import com.example.demo_be.base.service.impl.BaseServiceImpl;
import com.example.demo_be.common.CommonMethod;
import com.example.demo_be.entity.UserEntity;
import com.example.demo_be.exception.ValidationException;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.response.UserResponse;
import com.example.demo_be.util.ResponseUtil;

@Service
@Repository
public class UserServiceImpl extends BaseServiceImpl implements UserService {

   @Autowired
   UserRepository userRepository;

   @Override
   public UserResponse createUser(UserRequest req, String username) {

      UserResponse response = new UserResponse();

      if (StringUtils.isEmpty(req.getUsername())) {
         throw new ValidationException("COMMNERR00004", "Username");
      }

      if (StringUtils.isEmpty(req.getFullName())) {
         throw new ValidationException("COMMNERR00004", "Full Name");
      }

      if (StringUtils.isEmpty(req.getPassword())) {
         throw new ValidationException("COMMNERR00004", "Password");
      }



      if (StringUtils.isEmpty(req.getEmail())) {
         throw new ValidationException("COMMNERR00004", "Email");
      }

      UserEntity cekIdUser = userRepository.findById(req.getUsername()).orElse(null);

      if (cekIdUser != null && !cekIdUser.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00006", "Username", req.getUsername());
      }

      UserEntity entity = new UserEntity();
      BeanUtils.copyProperties(req, entity);
      this.setCreateCommon(entity, username);


      userRepository.save(entity);

      BeanUtils.copyProperties(entity, response);

      return response;
   }

   @Override
   public UserResponse updateUser(UserRequest req, String username) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public UserResponse deletUser(String userId, String username) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public UserResponse getUser(String username) {

      UserResponse response = new UserResponse();
      UserEntity cekIdUser = userRepository.findById(username).orElse(null);

      if (cekIdUser == null || cekIdUser.getDeletedFlag()) {
         throw new ValidationException("COMMNERR00007", "Username", username);
      }

      BeanUtils.copyProperties(cekIdUser, response);
      response.setPassword(null);
      return response;
   }

   @Override
   public UserResponse searchUser(String username) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ResponseEntity<ResponseCustom<UserResponse>> signUp(UserRequest req) {

      UserResponse response = new UserResponse();

      if (StringUtils.isEmpty(req.getUsername())) {
         return CommonMethod.badReq("Username tidak boleh kosong ");
      }

      if (StringUtils.isEmpty(req.getFullName())) {
         return CommonMethod.badReq("Full name tidak boleh kosong ");
      }

      if (StringUtils.isEmpty(req.getPassword())) {
         return CommonMethod.badReq("Password tidak boleh kosong ");
      }

      //JIKA PASS DAN RETYPE PASS TIDAK SAMA
      if (!req.getPassword().equals(req.getRetypePassword())) {
         return CommonMethod.badReq("PASS DAN RETYPE PASS TIDAK SAMA !");
      }
      

      if (StringUtils.isEmpty(req.getEmail())) {
         return CommonMethod.badReq("Email tidak boleh kosong ");
      }

      UserEntity cekIdUser = userRepository.findById(req.getUsername()).orElse(null);

      if (cekIdUser != null && cekIdUser.getDeletedFlag() == false ) {
         return CommonMethod.badReq("Username sudah terpakai ");
      }

      UserEntity entity = new UserEntity();
      BeanUtils.copyProperties(req, entity);
      entity.setCreatedBy(req.getUsername());
      entity.setCreatedDt(new Date(System.currentTimeMillis()));
      entity.setDeletedFlag(false);

      //ENCODE PASS
      String encodedString = Base64.getEncoder().encodeToString(req.getPassword().getBytes());
      entity.setPassword(encodedString);

      //DECODE
      // byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
      // String decodedString = new String(decodedBytes);

      userRepository.save(entity);

      BeanUtils.copyProperties(entity, response);

      return CommonMethod.success(response);
   }

}
