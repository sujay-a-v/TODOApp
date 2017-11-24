package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailerImpl implements Mailer {
	
	@Autowired
	private MailSender mailSender;

	@Override
	public void send(String to, String message) {
		
		SimpleMailMessage simpleMailmessage=new SimpleMailMessage();
		simpleMailmessage.setFrom("snehajeevi555@gmail.com");
		simpleMailmessage.setTo(to);
		simpleMailmessage.setSubject("Rabbit");
		simpleMailmessage.setText(message);
		
		mailSender.send(simpleMailmessage);
		System.out.println("mail Sent");
		
	}

}
