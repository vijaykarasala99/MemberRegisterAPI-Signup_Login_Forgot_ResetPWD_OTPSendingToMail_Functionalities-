package com.vijayit.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vijayit.binding.ForgotPwdForm;
import com.vijayit.binding.LoginForm;
import com.vijayit.binding.ResetPwdForm;
import com.vijayit.binding.SignUpForm;
import com.vijayit.entity.RegisterEntity;
import com.vijayit.repo.RegisterRepo;
import com.vijayit.utility.EmailUtil;
import com.vijayit.utility.OtpUtils;

import jakarta.servlet.http.HttpSession;
@Service
public class RegisterServiceImpl implements RegisterService {

@Autowired
private RegisterRepo registerRepo;
@Autowired
private EmailUtil emailUtil;
@Autowired
private OtpUtils otpUtil;
@Autowired
private HttpSession session;

//Example of password encryption using BCryptPasswordEncoder
//@Autowired
//private PasswordEncoder passwordEncoder;



	@Override
	public String login(LoginForm form) {
        
		RegisterEntity entity= registerRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
        	
        if (entity == null) {
    			return "Invalid Credentials";
    	}
    		// create session and store user data in session
    		session.setAttribute("id", entity.getId());
    		return "success";
     }

	@Override
	public boolean signUp(SignUpForm form) {
	
		RegisterEntity user= registerRepo.findByEmail(form.getEmail());
		if(user!=null) {
			return false;
		}
		RegisterEntity entity = new RegisterEntity();
		BeanUtils.copyProperties(form, entity);
	    registerRepo.save(entity);
	    return true;
	}

	@Override
	public boolean forgotPwd(ForgotPwdForm form) {
      RegisterEntity user= registerRepo.findByEmail(form.getEmail());
      if(user==null) {
	     return false;
	     }
      
        String otp = otpUtil.generateOtp();
        session.setAttribute("resetOtp", otp);	
        
        String subject = "Recover Password With OTP";
        String message = "Please Don't Share This OTP... This Is Confidential !! : " + otp;
        emailUtil.sendEmail(form.getEmail(), subject, message);

        return true;
    }

	@Override
	public boolean resetPwd(ResetPwdForm form) {
       
		String otp = (String) session.getAttribute("resetOtp");
       
		if (otp != null && otp.equals(form.getOtp())){
	        if (form.getNewPwd().equals(form.getConfirmPwd())) {
 
        	// Reset the password in the database
            RegisterEntity user = registerRepo.findByEmail(form.getEmail());
            if (user != null) {
                
            	user.setPwd(form.getNewPwd());
                
                //if you want to store encrypted pwd use below line
                //user.setPwd(passwordEncoder.encode(form.getNewPwd()));

                registerRepo.save(user);

                // Clear the session attributes after successful reset
                session.removeAttribute("resetOtp");
                
                return true;
            }
        }
		}

        return false;
    }
	}
