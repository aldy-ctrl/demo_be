package com.example.demo_be.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailList {

	private String mailTo;
	private String mailSubject;
	private String mailCc;
	private String mailText;
	private String smsToNo;
	private String smsContent;
	private String waToNo;
	private String waContent;
	private String notiType;
	private Boolean htmlFormatFlag;

}
