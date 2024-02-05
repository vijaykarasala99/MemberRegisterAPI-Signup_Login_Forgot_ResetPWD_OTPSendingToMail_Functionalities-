package com.vijayit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vijayit.binding.ForgotPwdForm;
import com.vijayit.binding.LoginForm;
import com.vijayit.binding.ResetPwdForm;
import com.vijayit.binding.SignUpForm;
import com.vijayit.service.RegisterService;

@RestController
@RequestMapping("/")
public class RegisterController {
	  @Autowired
	    private RegisterService registerService;

	   // @Autowired
	   // private PasswordEncoder passwordEncoder;

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
	        String result = registerService.login(loginForm);
	        if ("success".equals(result)) {
	            return ResponseEntity.ok("Login successful");
	        } else {
	            return ResponseEntity.badRequest().body("Invalid Credentials");
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
}