package com.example.demo_be.util;

import java.util.Date;

import com.example.demo_be.base.response.Response;
import com.example.demo_be.base.response.ResponseStatus;

import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

   public <T> Response<T> generateResponseSuccess(String messageCode, T data, String... varValues) {
      Response<T> result = new Response<>(ResponseStatus.SUCCESS, new Date());
      result.setMessageCode(messageCode);
      result.setMessage("Processed Successfully");
      result.setData(data);

      return result;
   }

   public <T> Response<T> generateResponseSuccess(T data) {

      return this.generateResponseSuccess("COMMNINF00002", data);
   }

   public <T> Response<T> generateResponseNotSuccess(String messageCode) {
      Response<T> result = new Response<>(ResponseStatus.SUCCESS, new Date());
      result.setMessageCode(messageCode);
      result.setMessage("Processed Successfully");
      result.setData(null);

      return result;
   }

   public <T> Response<T> generateResponseNotSuccess(T data , String msg) {

      return this.generateResponseNotSuccess(msg);
   }

   public Response<Object> generateResponseSuccess() {
      return this.generateResponseSuccess(null);
   }

}
