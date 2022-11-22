package com.example.demo_be.base.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCustom<T> {
   private String status;
   private String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
   private T data;

  public ResponseCustom(String status, String message) {
     this.status = status;
     this.message = message;


  }
}
