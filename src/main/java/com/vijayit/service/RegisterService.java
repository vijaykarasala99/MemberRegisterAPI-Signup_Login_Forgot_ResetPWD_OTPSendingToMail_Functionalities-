package com.vijayit.service;

import com.vijayit.binding.ForgotPwdForm;
import com.vijayit.binding.LoginForm;
import com.vijayit.binding.ResetPwdForm;
import com.vijayit.binding.SignUpForm;


public interface RegisterService {

	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form);

	public boolean forgotPwd(ForgotPwdForm form);
	
	public boolean resetPwd(ResetPwdForm form);

}
