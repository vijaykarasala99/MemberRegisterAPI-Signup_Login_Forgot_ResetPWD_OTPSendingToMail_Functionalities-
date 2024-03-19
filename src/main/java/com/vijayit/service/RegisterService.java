package com.vijayit.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.vijayit.binding.ForgotPwdForm;
import com.vijayit.binding.LoginForm;
import com.vijayit.binding.ResetPwdForm;
import com.vijayit.binding.SignUpForm;
import com.vijayit.entity.RegisterEntity;


public interface RegisterService {
  
   public RegisterEntity update(Long id, MultipartFile file, String name, Long mobileno, String email, String pwd,
			LocalDate dob, LocalDateTime time);
	
	public RegisterEntity findUserByEmailAndPwd(String email,String pwd);
	
    public RegisterEntity getById(Long id);
	
	public void save(MultipartFile file, String name, Long mobileno, String email, String pwd, LocalDate dob,LocalDateTime time) ;
	
	public RegisterEntity login(LoginForm form);
	
	public boolean signUp(SignUpForm form);
	 

	public boolean forgotPwd(ForgotPwdForm form);
	
	public boolean resetPwd(ResetPwdForm form);
    
	public List<RegisterEntity> getAll();
	
	public RegisterEntity update(Long id, RegisterEntity entity);
	
	public String deleteUser(Long id);

	
}
