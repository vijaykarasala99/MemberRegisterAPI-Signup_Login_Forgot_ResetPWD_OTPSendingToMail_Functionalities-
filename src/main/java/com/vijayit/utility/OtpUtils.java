package com.vijayit.utility;

import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class OtpUtils {
	 
	public String generateOtp() {
	        
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);
	        return String.valueOf(otp);
	    }
	
	
	
}
