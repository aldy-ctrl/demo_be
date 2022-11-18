package com.example.demo_be.request;

import com.example.demo_be.base.search.BaseSearchRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeSearchRequest extends BaseSearchRequest {

   private String employeCd;
   private String employeName;

}
