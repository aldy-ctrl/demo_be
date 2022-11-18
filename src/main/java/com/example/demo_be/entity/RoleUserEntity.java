package com.example.demo_be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.demo_be.base.entity.CommonEntityD;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(RoleUserId.class)
@Table(name = "TB_ROLE_USER")
public class RoleUserEntity extends CommonEntityD {

   @Id
   @Column(name = "username", nullable = false)
   private String username;

   @Id
   @Column(name = "role_cd", nullable = false)
   private String roleCd;

}
