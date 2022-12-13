package com.example.demo_be.service.userService;

import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo_be.base.response.ResponseCustom;
import com.example.demo_be.base.service.impl.BaseServiceImpl;
import com.example.demo_be.common.CommonMethod;
import com.example.demo_be.constants.Constant;
import com.example.demo_be.entity.UserEntity;
import com.example.demo_be.exception.ValidationException;
import com.example.demo_be.repository.UserRepository;
import com.example.demo_be.request.UserRequest;
import com.example.demo_be.request.ValidateOtpRequest;
import com.example.demo_be.response.UserResponse;
import com.example.demo_be.service.sendMailservice.SendMailService;
import com.example.demo_be.vo.MailList;
import com.example.demo_be.vo.MailNotification;
import com.example.demo_be.vo.VerifVo;

@Service
@Repository
public class UserServiceImpl extends BaseServiceImpl implements UserService {

   @Autowired
   UserRepository userRepository;

   @PersistenceContext
   private EntityManager entityManager;

   @Autowired
   private SendMailService sendMailService;

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
   public ResponseEntity<ResponseCustom<UserResponse>> updateUser(UserRequest req, String username) {
      UserResponse response = new UserResponse();

      if (req.getUsername() == null || StringUtils.isBlank(req.getUsername())) {
         return CommonMethod.badReq(Constant.MSG_ERROR_USERNAME);
      }

      UserEntity user = userRepository.findById(req.getUsername()).orElse(null);

      if (user == null) {
         return CommonMethod.badReq(Constant.MSG_ERROR_USERNAME_NOT_FOUND);
      }

      BeanUtils.copyProperties(user, response);

      return CommonMethod.success(response);
   }

   @Override
   public UserResponse deletUser(String userId, String username) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ResponseEntity<ResponseCustom<UserResponse>> getUser(String username) {

      UserResponse response = new UserResponse();
      UserEntity cekIdUser = userRepository.findById(username).orElse(null);

      if (cekIdUser == null || cekIdUser.getDeletedFlag()) {
         return CommonMethod.badReq(Constant.MSG_ERROR_USERNAME_NOT_FOUND);
      }

      BeanUtils.copyProperties(cekIdUser, response);
      response.setPassword(null);

      return CommonMethod.success(response);
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
         return CommonMethod.badReq(Constant.MSG_ERROR_USERNAME);
      }

      if (StringUtils.isEmpty(req.getFullName())) {
         return CommonMethod.badReq(Constant.MSG_ERROR_FULL_NAME);
      }

      if (StringUtils.isEmpty(req.getPassword())) {
         return CommonMethod.badReq(Constant.MSG_ERROR_PASSWORD);
      }

      // JIKA PASS DAN RETYPE PASS TIDAK SAMA
      if (!req.getPassword().equals(req.getRetypePassword())) {
         return CommonMethod.badReq(Constant.MSG_PASSWORD_RETYPE_PASSWORD_TIDAK_SAMA);
      }

      if (StringUtils.isEmpty(req.getEmail())) {
         return CommonMethod.badReq(Constant.MSG_ERROR_EMAIL);
      }

      UserEntity cekEmailUser = userRepository.getUserWithEmail(req.getEmail());
      if (cekEmailUser != null) {
         return CommonMethod.badReq(Constant.MSG_ERROR_EMAIL_TERPAKAI);
      }

      if (req.isChangeEmail() == false) {
         UserEntity cekIdUser = userRepository.findById(req.getUsername()).orElse(null);

         if (cekIdUser != null && cekIdUser.getDeletedFlag() == false) {
            return CommonMethod.badReq(Constant.MSG_ERROR_USERNAME_TERPAKAI);
         }
      }
       
      UserEntity entity = userRepository.findById(req.getUsername()).orElse(null);
      if (entity == null) {
         entity = new UserEntity();
      }

      BeanUtils.copyProperties(req, entity);

      // ENCODE PASS
      String encodedString = Base64.getEncoder().encodeToString(req.getPassword().getBytes());
      entity.setPassword(encodedString);

      // [START] Send Mail
      Random rnd = new Random();
      int number = rnd.nextInt(99999);
      VerifVo vo = new VerifVo();
      vo.setEmail(req.getEmail());
      vo.setUsername(req.getUsername());
      vo.setVerifCode(String.valueOf(number));

      MailNotification notification = this.mailNotif(vo);
      try {
         entity.setOtpRegis(String.valueOf(number));
         entity.setRegisTime(new Date(System.currentTimeMillis()));
         entity.setFlagRegis(false);
         sendMailService.sendMail(notification);
      } catch (Exception e) {
         return CommonMethod.badReq("Exception Send Verif Code: ");
      }
      // [END] Send Mail

      this.setCreateCommon(entity, req.getUsername());
      userRepository.save(entity);

      BeanUtils.copyProperties(entity, response);

      return CommonMethod.success(response);
   }

   private MailNotification mailNotif(VerifVo vo) {

      MailNotification noti = new MailNotification();
      noti.setUserName(vo.getUsername());
      noti.setSourceTypeCd("REGIS USER");
      noti.setTransTypeCd("REGIS USER");

      MailList mails = new MailList();
      mails.setMailTo(vo.getEmail());

      mails.setMailSubject("Email");

      mails.setMailText(this.mailNotificationMsgContent(vo));
      mails.setNotiType("NT_EMAIL");
      mails.setHtmlFormatFlag(true);
      noti.setMails(mails);

      return noti;
   }

   private String mailNotificationMsgContent(VerifVo vo) {

      StringBuilder sb = new StringBuilder();

      sb.append("<p align = \"left\">Name : " + vo.getUsername() + " </p>");
      sb.append("<p align = \"left\">Email : " + vo.getEmail() + "</p>");
      sb.append("<hr>");
      sb.append("<p align = \"center\">Verif Code : </p>");
      sb.append("<h4  style= \"font-size:32px;\"> " + vo.getVerifCode() + " <h4/>");

      String mailContent = this.getHtlm();
      mailContent = mailContent.replace("{content}", sb.toString());

      return mailContent;
   }

   private String getHtlm() {

      StringBuilder sb = new StringBuilder();

      sb.append("select system_value from tmsupplier.tb_r_system trs where system_cd = 'HTML_EMAIL_REGIS'");
      Query query = entityManager.createNativeQuery(sb.toString());
      String result = (String) query.getSingleResult();

      return result;

   }

   @Override
   public ResponseEntity<ResponseCustom<UserResponse>> validateOtpCode(ValidateOtpRequest request) {
      UserEntity user = userRepository.findById(request.getUsername()).orElse(null);

      UserResponse resp = new UserResponse();

      if (user == null) {
         return CommonMethod.badReq("User tidak ditemukan ");
      }

      if (!user.getOtpRegis().equals(request.getOtpCode())) {
         return CommonMethod.badReq("Otp yang anda masukan salah ");

      }else{
         user.setFlagRegis(true);
         user.setRegisTime(new Date(System.currentTimeMillis()));
         userRepository.save(user);

         BeanUtils.copyProperties(user, resp);
      }
      


      return CommonMethod.success(resp);
   }

}
