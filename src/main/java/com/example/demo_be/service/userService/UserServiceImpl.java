package com.example.demo_be.service.userService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo_be.base.service.impl.BaseServiceImpl;
import com.example.demo_be.entity.UserEntity;
import com.example.demo_be.exception.ValidationException;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.response.UserResponse;

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

}
