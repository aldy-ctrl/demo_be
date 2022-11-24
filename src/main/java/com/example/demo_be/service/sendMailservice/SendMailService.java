package com.example.demo_be.service.sendMailservice;

import com.example.demo_be.vo.MailNotification;
import javax.mail.MessagingException;

public interface SendMailService {

	public void sendMail(MailNotification mailNotification) throws MessagingException;

}
