package com.example.demo_be.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {

   private String employeeCd;
   private String employeeName;
   private String employeeAddr;
   private String employeeContacNo;
   private String employeeDesc;
}
