package com.example1.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example1.demo.binding.DashBoard;
import com.example1.demo.binding.LoginData;
import com.example1.demo.binding.UnlockData;
import com.example1.demo.binding.UserRegiData;
import com.example1.demo.entity.UserEntity;
import com.example1.demo.repo.UserRepo;
import com.example1.demo.utility.EmailUtility;
import com.example1.demo.utility.PwdUtility;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
   private UserRepo repo;
	@Autowired
	private PwdUtility pwdutility;
	@Autowired
	private EmailUtility utility;
	@Autowired
  private 	HttpSession session;
	
	
	public String loginData(LoginData data) {
	UserEntity entity=repo.findByEmailAndPassword(data.getEmail(), data.getPassword());
	     if(entity==null) {
	    	 return "Invalid credentials";
	     }
	     if(entity.getAccStatus().equals("Locked")) {
	    	 return "your account is locked";
	    	 
	     }
	     
	     //session created 
	     session.setAttribute("id", entity.getId());
		return "success";
	}


	public boolean signup(UserRegiData data) {
		
	UserEntity	user=repo.findByEmail(data.getEmail());
	if(user !=null) {
		return false;
	}
		
		UserEntity entity=new UserEntity();
		BeanUtils.copyProperties(data, entity);
	    String temp=pwdutility.generatePwd();
		entity.setPassword(temp);
		entity.setAccStatus("Locked");
		repo.save(entity);
		
		String to=entity.getEmail();
		
		
		
		String subject="Unlock your Account ! Ashok It";
		
		StringBuffer body=new StringBuffer("");
		body.append("<h1> Use Below temporary pwd to unlock your account </h1>");
		body.append("Temporary pwd "+temp);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+ to +"\">Click here to unlockyour</a>");
		utility.sendEmail(to, body.toString(), subject);
		return true;
	}


	
	
	
	
	public boolean unlock(UnlockData data) {
		UserEntity entity=repo.findByEmail(data.getEmail());
		if(entity.getPassword().equals(data.getTempPwd())) {
			entity.setAccStatus("Unlocked");
			entity.setPassword(data.getNewPwd());
			repo.save(entity);
			return true;
		}else {
		return false;
		}
	}

	
	public String dashBoard(DashBoard data) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public boolean forgotpwd(String email) {
		UserEntity entity=repo.findByEmail(email);
		if(entity==null) {
			return false;
		}
		
		String subject="Recover password";
		String body="your pwd "+entity.getPassword();
		utility.sendEmail(email, body, subject);
		return true;
	}

}
