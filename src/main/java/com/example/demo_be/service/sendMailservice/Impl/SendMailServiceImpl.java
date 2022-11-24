package com.example.demo_be.service.sendMailservice.Impl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo_be.service.sendMailservice.SendMailService;
import com.example.demo_be.vo.MailList;
import com.example.demo_be.vo.MailNotification;
import com.example.demo_be.vo.VerifVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Repository
public class SendMailServiceImpl implements SendMailService {

	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.username}")
	private String mailFrom;

	@Override
	public void sendMail(MailNotification mailNotification) throws MessagingException {
		log.info("mailProperties: " + mailNotification);

		try {
			MimeMessage message = emailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(mailFrom);

			String mailToStr = mailNotification.getMails().getMailTo();
			String[] mailToArray = mailToStr.split(",");
			helper.setTo(mailToArray);

			if (!StringUtils.isEmpty(mailNotification.getMails().getMailCc())) {
				String mailCcStr = mailNotification.getMails().getMailCc();
				String[] mailCcArray = mailCcStr.split(",");
				helper.setCc(mailCcArray);
			}

			helper.setSubject(mailNotification.getMails().getMailSubject());

			if (mailNotification.getMails().getHtmlFormatFlag()) {
				helper.setText(mailNotification.getMails().getMailText(), true);
			} else {
				helper.setText(mailNotification.getMails().getMailText());
			}

			emailSender.send(message);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
