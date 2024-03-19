package com.vijayit.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vijayit.binding.ForgotPwdForm;
import com.vijayit.binding.LoginForm;
import com.vijayit.binding.ResetPwdForm;
import com.vijayit.binding.SignUpForm;
import com.vijayit.entity.RegisterEntity;
import com.vijayit.repo.RegisterRepo;
import com.vijayit.utility.EmailUtil;
import com.vijayit.utility.OtpUtils;

import jakarta.persistence.EntityNotFoundException;
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
//private PasswordEncoder passwordEncoder;\

@Override
public RegisterEntity getById(Long id) {
	Optional<RegisterEntity> findById = registerRepo.findById(id);
	if (findById.isPresent()) {
		return findById.get();
	}
	return null;
}


@Override
public RegisterEntity update(Long id, MultipartFile file, String name, Long mobileno, String email, String pwd,
        LocalDate dob, LocalDateTime time) {
	RegisterEntity existingEntity = registerRepo.findById(id)
    .orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " not found"));

    // Update the entity fields with the provided parameters
    existingEntity.setName(name);
    existingEntity.setMobileno(mobileno);
    existingEntity.setEmail(email);
    existingEntity.setPwd(pwd);
    existingEntity.setDob(dob);
    existingEntity.setTime(time);
    try {
    	existingEntity.setPic(Base64.getEncoder().encodeToString(file.getBytes()));
	} catch (IOException e) {
		e.printStackTrace();
	}
    return registerRepo.save(existingEntity);
}





@Override
public RegisterEntity findUserByEmailAndPwd(String email, String pwd) {
    RegisterEntity entity = registerRepo.findByEmailAndPwd(email, pwd);
    if (entity != null) {
        RegisterEntity newUser = new RegisterEntity();
        newUser.setId(entity.getId());
        newUser.setName(entity.getName());
        return newUser;
    } else {
        return null;
    }
}



@Override
public void save(MultipartFile file, String name, Long mobileno, String email, String pwd, LocalDate dob,
		LocalDateTime time) {
   

        RegisterEntity entity = new RegisterEntity();
        entity.setName(name);
        entity.setEmail(email);
        entity.setDob(dob);
        entity.setMobileno(mobileno);
        entity.setPwd(pwd);
        entity.setTime(time);
        try {
			entity.setPic(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        registerRepo.save(entity);
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
	public RegisterEntity login(LoginForm form) {
        
		  RegisterEntity entity = registerRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		    
		    if (entity == null) {
		        return null;
		    }
		    session.setAttribute("id", entity.getId());
		    return entity;
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

	@Override
	public List<RegisterEntity> getAll() {
		return registerRepo.findAll();
	}

	@Override
	public RegisterEntity update(Long id,RegisterEntity entity) {
		Optional<RegisterEntity> existingusers = registerRepo.findById(id);
		if (existingusers.isPresent()) {
			RegisterEntity existinguser = existingusers.get();   //first get complete entity
			existinguser.setName(entity.getName());              //next update each property 
			existinguser.setPic(entity.getPic());
			existinguser.setEmail(entity.getEmail());
			existinguser.setMobileno(entity.getMobileno());
			existinguser.setPwd(entity.getPwd());
			existinguser.setDob(entity.getDob());
			existinguser.setTime(entity.getTime());
			return registerRepo.save(existinguser);
			
	    } else {
	        return null;
	    }
	}   
	
	@Override
	public String deleteUser(Long id) {
			if (registerRepo.existsById(id)) {
			registerRepo.deleteById(id);
			return "User Deleted Successfully!";
			} else {
				return "User Not Found!";
			}}
	}