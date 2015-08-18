package com.sourabh.businessService;

import com.sourabh.DTO.LoginDTO;
import com.sourabh.DTO.RegisterDTO;
import com.sourabh.DTO.UserDetailsDTO;
import com.sourabh.entity.User;

public interface AuthenticationInterface {

	public abstract UserDetailsDTO doLogin(LoginDTO loginDTO);
	public abstract UserDetailsDTO doRegister(RegisterDTO loginDTO);
	public abstract String forgotPassword(String userName);
}
