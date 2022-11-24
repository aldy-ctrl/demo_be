package com.example.demo_be.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailNotification {

	private String userName;
	private String sourceTypeCd;
	private String transTypeCd;

	MailList mails;

}
