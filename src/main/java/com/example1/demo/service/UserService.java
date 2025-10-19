package com.example1.demo.service;



import com.example1.demo.binding.DashBoard;
import com.example1.demo.binding.LoginData;
import com.example1.demo.binding.UnlockData;
import com.example1.demo.binding.UserRegiData;


public interface UserService {
	
	public String loginData(LoginData data);
	
	public boolean signup(UserRegiData data);
	
	public boolean unlock(UnlockData data);
	
	public String dashBoard(DashBoard data);
	
     public boolean forgotpwd(String email);
}
