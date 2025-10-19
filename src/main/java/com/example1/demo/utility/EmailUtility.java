package com.example1.demo.utility;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtility {
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to,String body,String subject) {
		boolean issent=false;
		try {
			
		MimeMessage	mimeMessage=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
		helper.setTo(to);
		helper.setText(body,true);
		helper.setSubject(subject);
			mailSender.send(mimeMessage);
			issent=true;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return issent;
	}

}
