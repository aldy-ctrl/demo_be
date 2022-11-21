package com.example.demo_be.entity;

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
@Table(name = "tb_r_message", schema = "tmsupplier")
public class MessageValidEntity extends CommonEntity {

   @Id
   @Column(name = "msg_cd", nullable = false)
   private String msgCd;

   @Column(name = "msg_type_cd")
   private String msgTypeCd;

   @Column(name = "msg_text")
   private String msgText;

   @Column(name = "msg_icon")
   private String msgIcon;

   @Column(name = "msg_desc")
   private String msgDesc;

}
