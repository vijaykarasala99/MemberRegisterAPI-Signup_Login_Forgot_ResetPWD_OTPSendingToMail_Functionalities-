package com.vijayit.binding;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsersForm {
	
	private Long id;
	private String name;
	private byte[] pic;
	// need to verify otp
	private Long mobileno;
	// need to verify otp
	private String email;
	private String pwd;
	private LocalDate dob;
	private LocalDateTime time;

}
