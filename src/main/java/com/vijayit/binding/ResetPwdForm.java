package com.vijayit.binding;

import lombok.Data;

@Data
public class ResetPwdForm {
	
	private String email;
	private String otp;
	private String newPwd;
	private String confirmPwd;

}
