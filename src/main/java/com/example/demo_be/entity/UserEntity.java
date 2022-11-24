package com.example.demo_be.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo_be.base.entity.CommonEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_r_user", schema = "tmsupplier")
public class UserEntity extends CommonEntity {

   @Id
   @Column(name = "username", nullable = false)
   private String username;

   @Column(name = "person_name", nullable = false)
   private String fullName;

   @Column(name = "pass", nullable = false)
   private String password;

   @Column(name = "email", nullable = false)
   private String email;

   @Column(name = "otp_regis")
   private String otpRegis;

   @Column(name = "regis_flag")
   private Boolean flagRegis;

   @Column(name = "otp_reset")
   private String otpReset;

   @Column(name = "reset_flag")
   private Boolean flagReset;

   @Column(name = "regis_time")
   private Date regisTime;

   @Column(name = "reset_time")
   private Date resetTime;

}
