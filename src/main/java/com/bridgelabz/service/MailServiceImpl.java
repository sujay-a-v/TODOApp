package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailServiceImpl implements MailService {
	
	@Autowired
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendMail(String to,String subject, String msg) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		String from="sujay.av555@gmail.com";
		//String subject="User Validate";

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		System.out.println("msg sent");
		mailSender.send(message);
		
	}

}
