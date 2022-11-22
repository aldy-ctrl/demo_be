package com.example.demo_be.common;

import com.example.demo_be.base.response.ResponseCustom;
import com.example.demo_be.constants.Constant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CommonMethod {

	private CommonMethod() {
	    throw new IllegalStateException("Utility class");
	}

	public static <T> ResponseEntity<ResponseCustom<T>> success(T data) {
		return ResponseEntity.ok(new ResponseCustom<>(Constant.STATUS_SUCCESS_CODE, Constant.MESSAGE_SUCCESS, data));
	}

	public static <T> ResponseEntity<ResponseCustom<T>> badReq(String message) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseCustom<>(Constant.STATUS_ERROR_CODE, message));
	}

	public static ResponseEntity<ResponseCustom> badRequest(String message) {
		ResponseCustom<Object> response = new ResponseCustom<>();
		response.setStatus(Constant.STATUS_ERROR_CODE);
		response.setMessage(message);
		response.setData(null);

		return  ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .contentType(MediaType.APPLICATION_JSON)
	                .body(response);
	}
	

	
}

