package com.example1.demo.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PwdUtility {

	public static  String generatePwd() {
		String characterts="QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm";
	String pwd=	RandomStringUtils.random(6, characterts);
	
		return pwd;
	}
	
	
}
