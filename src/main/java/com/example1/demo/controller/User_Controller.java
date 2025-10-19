package com.example1.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example1.demo.binding.LoginData;
import com.example1.demo.binding.UnlockData;
import com.example1.demo.binding.UserRegiData;
import com.example1.demo.service.UserService;

@Controller
public class User_Controller {
	@Autowired
	private UserService service;
	
	
	@PostMapping("/signup")
	 public String signUp(@ModelAttribute("user") UserRegiData data,Model model) {
		
		boolean status= service.signup(data);
		if(status) {
			model.addAttribute("success", "check ur mail");
			
		}else {
			model.addAttribute("err", "pls enter unique email");
		}
		
		 return "signup";
	 }

	@GetMapping("/signup")
	 public String signUp(Model model) {
		model.addAttribute("user", new UserRegiData());
		 
		 return "signup";
	 }
	@GetMapping("/unlock")
	public String unlockForm(Model model,@RequestParam String email) {
		UnlockData data=new UnlockData();
		data.setEmail(email);
		model.addAttribute("unlockobj", data);// for empty unlock form alredy object is created for UnlockDta class
		return "unlock";
	}
	@PostMapping("/unlock")
	public String unlockForm(@ModelAttribute("unlockobj")  UnlockData data,Model model) {
		if(data.getConPwd().equals(data.getNewPwd())){
		boolean status=	service.unlock(data);
		 if(status) {
			 model.addAttribute("sucess", "unlock sucess ");
		 }else {
			 model.addAttribute("err", "temp pwd is incorrect");
		 }
		
		}else {
		model.addAttribute("err", "new pwd and conpwd not same");
		}
		return "unlock";
		
	}
	
	
	

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginform", new LoginData());
		return "login";
	}
	
	
	
	@PostMapping("/login")
	public String loginform(@ModelAttribute("loginform") LoginData data,Model model) {
		
	String status=service.loginData(data);
	if(status.contains("success")) {
	  return "redirect:/dashboard";
	}
	model.addAttribute("err", status);
	
		return "login";
	}
	
	
	
	@GetMapping("/forgot")
	public String forgetPwdpage() {
		return "forgotPwd";
	}
	
	@PostMapping("/forgot")
	public String forgetPwd(@RequestParam String email,Model model) {
		
	boolean status	=service.forgotpwd(email);
		
		if(status) {
			model.addAttribute("success", "pwd send to email");
		}else {
			model.addAttribute("err", "invalid credentials");
		}
		return "forgotPwd";
	}
	
}
