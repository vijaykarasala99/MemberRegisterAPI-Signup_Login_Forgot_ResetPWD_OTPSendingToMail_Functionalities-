package com.vijayit.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vijayit.binding.ForgotPwdForm;
import com.vijayit.binding.LoginForm;
import com.vijayit.binding.ResetPwdForm;
import com.vijayit.binding.SignUpForm;
import com.vijayit.binding.UserInfo;
import com.vijayit.entity.RegisterEntity;
import com.vijayit.service.RegisterService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class RegisterController {
   
   @Autowired
   private RegisterService registerService;

// @Autowired
// private PasswordEncoder passwordEncoder;
   
   
   @PutMapping("/update/{id}")
   public ResponseEntity<?> updateUser(@PathVariable Long id,
                                       @RequestParam(value = "pic", required = false) MultipartFile file,
                                       @RequestParam(value= "pwd", required = false) String pwd,
                                       @RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam("mobileno") Long mobileno,
                                       @RequestParam("dob") String dob,
                                       @RequestParam("time") String time) throws IOException {

       LocalDate dateOfBirth = LocalDate.parse(dob);
       LocalDateTime dateTime = LocalDateTime.parse(time);

       registerService.update(id, file, name, mobileno, email, pwd, dateOfBirth, dateTime);
       return ResponseEntity.ok().build();
   }

   
   @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	  public ResponseEntity<String> saveBird(
	    		@RequestParam("pic") MultipartFile file,
	            @RequestParam("name") String name,
	            @RequestParam("mobileno") Long mobileno,
	            @RequestParam("email") String email,
	            @RequestParam("pwd") String pwd,
	            @RequestParam("dob") LocalDate dob,
	            @RequestParam("time")LocalDateTime time) {
	        try {
	        	registerService.save(file, name, mobileno, email, pwd, dob, time);
	            return ResponseEntity.ok().body("saved successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving: " + e.getMessage());
	        }
	    }
    
   
   
   @GetMapping("/get/{id}")
	public RegisterEntity getById(@PathVariable Long id) {
		return registerService.getById(id);
      }
   
   
   @PostMapping("/fetch")
   public Object LoggedIn(@RequestBody LoginForm form) {
       RegisterEntity user = registerService.findUserByEmailAndPwd(form.getEmail(), form.getPwd());
       if (user != null) {
           return new UserInfo(user.getId(),user.getName());
       } else {
           return "Invalid email or password.";
       }
   }
   
 
  
	  @PostMapping("/signup")
	    public ResponseEntity<String> signUp(@RequestBody SignUpForm signUpForm) {
	        if (registerService.signUp(signUpForm)) {
	            return ResponseEntity.ok("User registered successfully");
	        } else {
	            return ResponseEntity.badRequest().body("User with the provided email already exists");
	        }
	    }

	  @PostMapping("/login")
	  public ResponseEntity<RegisterEntity> login(@RequestBody LoginForm loginForm) {
	      RegisterEntity user = registerService.login(loginForm);
	      if (user != null) {
	          return ResponseEntity.ok(user);
	      } else {
	          return ResponseEntity.badRequest().body(null);
	      }
	  }

	    @PostMapping("/forgot")
	    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPwdForm forgotPwdForm) {
	        if (registerService.forgotPwd(forgotPwdForm)) {
	            return ResponseEntity.ok("Password reset email sent successfully");
	        } else {
	            return ResponseEntity.badRequest().body("User with the provided email not found");
	        }
	    }

	    @PostMapping("/reset")
	    public ResponseEntity<String> resetPassword(@RequestBody ResetPwdForm resetPwdForm) {
	        if (registerService.resetPwd(resetPwdForm)) {
	            return ResponseEntity.ok("Password reset successfully");
	        } else {
	            return ResponseEntity.badRequest().body("Invalid OTP or email. Please try again");
	        }
	    }
	     
	    @GetMapping("/getall")
	    public List<RegisterEntity> getAll() {
	        return registerService.getAll();
    }
	  
	    @DeleteMapping("/delete/{id}")
		public String deleteUser(@PathVariable Long  id) {
	    	return registerService.deleteUser(id);
	    	
}    }