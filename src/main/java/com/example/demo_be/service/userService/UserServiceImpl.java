package com.example.demo_be.service.userService;

import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import com.example.demo_be.service.sendMailservice.SendMailService;
import com.example.demo_be.util.ResponseUtil;
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

      // JIKA PASS DAN RETYPE PASS TIDAK SAMA
      if (!req.getPassword().equals(req.getRetypePassword())) {
         return CommonMethod.badReq("Pass dan retype Pass tidak sama ");
      }

      if (StringUtils.isEmpty(req.getEmail())) {
         return CommonMethod.badReq("Email tidak boleh kosong ");
      }

      UserEntity cekIdUser = userRepository.findById(req.getUsername()).orElse(null);

      if (cekIdUser != null && cekIdUser.getDeletedFlag() == false) {
         return CommonMethod.badReq("Username sudah terpakai ");
      }

      UserEntity entity = new UserEntity();
      BeanUtils.copyProperties(req, entity);
      entity.setCreatedBy(req.getUsername());
      entity.setCreatedDt(new Date(System.currentTimeMillis()));
      entity.setDeletedFlag(false);

      // ENCODE PASS
      String encodedString = Base64.getEncoder().encodeToString(req.getPassword().getBytes());
      entity.setPassword(encodedString);

      // Send Mail

      Random rnd = new Random();
      int number = rnd.nextInt(999999);

      VerifVo vo = new VerifVo();
      vo.setEmail(req.getEmail());
      vo.setUsername(req.getUsername());
      vo.setVerifCode(String.valueOf(number));

      MailNotification notification = this.mailNotif(vo);

      try {
         sendMailService.sendMail(notification);
      } catch (MessagingException e) {
         return CommonMethod.badReq("Exception Send Verif Code: ");
      }

      userRepository.save(entity);

      BeanUtils.copyProperties(entity, response);

      return CommonMethod.success(response);
   }

   private MailNotification mailNotif(VerifVo vo) {

      VerifVo vos = new VerifVo();
      vos.setUsername(vo.getUsername());
      vos.setEmail(vo.getEmail());
      vos.setVerifCode(vo.getVerifCode());

      MailNotification noti = new MailNotification();
      noti.setUserName(vo.getUsername());
      noti.setSourceTypeCd("REGIS USER");
      noti.setTransTypeCd("REGIS USER");

      MailList mails = new MailList();
      mails.setMailTo(vo.getEmail());

      mails.setMailSubject("Email");

      mails.setMailText(this.mailNotificationMsgContent(null));
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
      sb.append("<p align = \"left\">Message : </p>");
      sb.append("<h4>" + vo.getVerifCode() + "<h4/>");

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

}
