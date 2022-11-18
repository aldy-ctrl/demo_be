package com.example.demo_be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "TB_M_USER")
public class UserEntity extends CommonEntityD {

   @Id
   @Column(name = "username", nullable = false)
   private String username;

   @Column(name = "pass", nullable = false)
   private String pass;

}
